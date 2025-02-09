/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard;

import dev.neuralnexus.modapi.metadata.Logger;
import dev.neuralnexus.modapi.metadata.MetaAPI;
import dev.neuralnexus.modapi.metadata.Platform;
import dev.neuralnexus.modapi.metadata.Platforms;
import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.DiscordModule;
import dev.neuralnexus.switchboard.modules.minecraft.MinecraftModule;
import dev.neuralnexus.switchboard.modules.proxy.ProxyModule;
import dev.neuralnexus.switchboard.modules.telegram.TelegramModule;
import dev.neuralnexus.switchboard.modules.webhook.WebhookModule;
import dev.neuralnexus.switchboard.modules.websocket.WebSocketModule;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.event.api.ServerEvents;
import dev.neuralnexus.taterapi.metrics.bstats.BStatsMetrics;
import dev.neuralnexus.taterapi.metrics.bstats.MetricsAdapter;
import dev.neuralnexus.taterapi.loader.Loader;
import dev.neuralnexus.taterapi.event.api.PluginEvents;
import dev.neuralnexus.taterapi.loader.plugin.ModuleLoader;
import dev.neuralnexus.taterapi.loader.plugin.Plugin;
import dev.neuralnexus.taterapi.loader.plugin.impl.ModuleLoaderImpl;

import java.util.HashMap;

/** Main class for the plugin. */
public class Switchboard implements Plugin {
    public static final String PROJECT_NAME = "Switchboard";
    public static final String PROJECT_ID = "switchboard";
    public static final String PROJECT_VERSION = "2.0.0-SNAPSHOT";
    public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
    public static final String PROJECT_DESCRIPTION =
            "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.";
    public static final String PROJECT_URL = "https://github.com/p0t4t0sandwich/Switchboard";

    private static final Switchboard instance = new Switchboard();
    private static final Logger logger = Logger.create(PROJECT_ID);
    private static final ModuleLoader moduleLoader = new ModuleLoaderImpl();
    private static BStatsMetrics metrics;
    private static boolean RELOADED = false;

    public static Logger logger() {
        return logger;
    }

    /**
     * Get if the plugin has reloaded
     *
     * @return If the plugin has reloaded
     */
    public static boolean hasReloaded() {
        return RELOADED;
    }

    /**
     * Getter for the singleton instance of the class.
     *
     * @return The singleton instance
     */
    public static Switchboard instance() {
        return instance;
    }

    @Override
    public String name() {
        return Switchboard.PROJECT_NAME;
    }

    @Override
    public String id() {
        return Switchboard.PROJECT_ID;
    }

    @Override
    public void onEnable() {
        MetaAPI api = MetaAPI.instance();
        logger.info(
                PROJECT_NAME
                        + " is running on "
                        + api.platform().asString()
                        + " "
                        + api.version().asString()
                        + ", with "
                        + api.mappings().toString()
                        + " mappings!");
        PluginEvent.DISABLED.register(event -> onDisable());

        Loader loader = Loader.instance();

        // Set up bStats
        HashMap<Platform, Integer> statsMap = new HashMap<>();
        statsMap.put(Platforms.BUKKIT, 21170);
        statsMap.put(Platforms.BUNGEECORD, 21171);
        statsMap.put(Platforms.SPONGE, 21172);
        statsMap.put(Platforms.VELOCITY, 21173);
        metrics =
                MetricsAdapter.setupMetrics(
                        loader.plugin(), loader.server(), logger.getLogger(), statsMap);

        // Config
        SwitchboardConfigLoader.load();

        // Register API
        SwitchboardAPIProvider.register(new SwitchboardAPI());

        if (!RELOADED) {
            ServerEvents.STOPPED.register(event -> metrics.shutdown());

            // Register modules
            if (SwitchboardConfigLoader.config().checkModule("minecraft")) {
                moduleLoader.registerModule(new MinecraftModule());
            }
            if (SwitchboardConfigLoader.config().checkModule("discord")) {
                moduleLoader.registerModule(new DiscordModule());
            }
            if (SwitchboardConfigLoader.config().checkModule("proxy")) {
                moduleLoader.registerModule(new ProxyModule());
            }
            if (SwitchboardConfigLoader.config().checkModule("telegram")) {
                moduleLoader.registerModule(new TelegramModule());
            }
            if (SwitchboardConfigLoader.config().checkModule("webhook")) {
                moduleLoader.registerModule(new WebhookModule());
            }
            if (SwitchboardConfigLoader.config().checkModule("websocket")) {
                moduleLoader.registerModule(new WebSocketModule());
            }
        }

        moduleLoader.onEnable();
        logger.info("Starting modules: " + moduleLoader.moduleNames());
        logger.info(PROJECT_NAME + " has been started!");
    }

    @Override
    public void onDisable() {
        // Remove references to objects
        SwitchboardConfigLoader.unload();

        // Unregister API
        SwitchboardAPIProvider.unregister();

        moduleLoader.onDisable();

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
