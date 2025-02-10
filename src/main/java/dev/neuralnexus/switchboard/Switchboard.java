/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard;

import dev.neuralnexus.switchboard.api.Message;
import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.config.SwitchboardConfig;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;

import net.engio.mbassy.bus.MBassador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main class for Switchboard. */
public class Switchboard {
    private static final Switchboard instance = new Switchboard();
    private static final MBassador<Message> bus = new MBassador<>();
    public static final String PROJECT_NAME = "Switchboard";
    public static final String PROJECT_ID = "switchboard";
    private static final Logger logger = LoggerFactory.getLogger(PROJECT_ID);
    private static boolean RELOADED = false;

    /**
     * Getter for the singleton instance of the class
     *
     * @return The singleton instance
     */
    public static Switchboard instance() {
        return instance;
    }

    /**
     * Getter for the logger
     *
     * @return The logger
     */
    public static Logger logger() {
        return logger;
    }

    /**
     * Getter for the event bus
     *
     * @return The event bus
     */
    public static MBassador<Message> bus() {
        return bus;
    }

    public void onEnable() {
        // Config
        SwitchboardConfigLoader.load();

        // Register API
        new SwitchboardAPI();

        if (!RELOADED) {
            SwitchboardConfig config = SwitchboardConfigLoader.config();
            if (config.checkModule("discord")) {
                String token = SwitchboardConfigLoader.config().discord().token();
                if (token == null || token.isEmpty()) {
                    throw new RuntimeException("No Discord token found in switchboard.conf!");
                }
                SwitchboardAPI.instance().discordAPI().startBot();
            } else if (config.checkModule("telegram")) {
                String token = SwitchboardConfigLoader.config().telegram().token();
                if (token == null || token.isEmpty()) {
                    throw new RuntimeException("No Telegram token found in switchboard.conf!");
                }
                SwitchboardAPI.instance().telegramAPI().startBot();
            } else if (config.checkModule("websocket")) {
                WebSocketConfig wsConfig = config.webSocket();
                if (wsConfig.host() == null || wsConfig.host().isEmpty()) {
                    throw new RuntimeException("No WebSocket host found in switchboard.conf!");
                }
                if (wsConfig.port() == 0) {
                    throw new RuntimeException("No WebSocket port found in switchboard.conf!");
                }
            }
        }

        logger.info(PROJECT_NAME + " has been started!");
    }

    public void onDisable() {
        // Remove references to objects
        SwitchboardConfig config = SwitchboardConfigLoader.config();
        if (config.checkModule("discord")) {
            SwitchboardAPI.instance().discordAPI().removeBot();
        } else if (config.checkModule("telegram")) {
            SwitchboardAPI.instance().telegramAPI().removeBot();
        } else if (config.checkModule("websocket")) {
            SwitchboardAPI.instance().webSocketAPI().stopWebSocket();
        }

        SwitchboardConfigLoader.unload();

        // Unregister API
        SwitchboardAPI.unregister();

        logger.info(PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public void reload() {
        RELOADED = true;
        this.onDisable();
        this.onEnable();
        logger.info(PROJECT_NAME + " has been reloaded!");
    }
}
