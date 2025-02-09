/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard;

import dev.neuralnexus.switchboard.api.Message;
import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfig;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.DiscordModule;
import dev.neuralnexus.switchboard.modules.minecraft.MinecraftModule;
import dev.neuralnexus.switchboard.modules.proxy.ProxyModule;
import dev.neuralnexus.switchboard.modules.telegram.TelegramModule;
import dev.neuralnexus.switchboard.modules.webhook.WebhookModule;
import dev.neuralnexus.switchboard.modules.websocket.WebSocketModule;

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
        SwitchboardAPIProvider.register(new SwitchboardAPI());

        // EventBus

        if (!RELOADED) {
            SwitchboardConfig config = SwitchboardConfigLoader.config();

            // Register modules
            if (config.checkModule("discord")) {
                moduleLoader.registerModule(new DiscordModule());
            }
            if (config.checkModule("proxy")) {
                moduleLoader.registerModule(new ProxyModule());
            }
            if (config.checkModule("telegram")) {
                moduleLoader.registerModule(new TelegramModule());
            }
            if (config.checkModule("webhook")) {
                moduleLoader.registerModule(new WebhookModule());
            }
            if (config.checkModule("websocket")) {
                moduleLoader.registerModule(new WebSocketModule());
            }
        }

        logger.info(PROJECT_NAME + " has been started!");
    }

    public void onDisable() {
        // Remove references to objects
        SwitchboardConfigLoader.unload();

        // Unregister API
        SwitchboardAPIProvider.unregister();

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
