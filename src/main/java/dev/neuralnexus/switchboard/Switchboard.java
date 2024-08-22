/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard;

import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.DiscordModule;
import dev.neuralnexus.switchboard.modules.minecraft.MinecraftModule;
import dev.neuralnexus.switchboard.modules.proxy.ProxyModule;
import dev.neuralnexus.switchboard.modules.telegram.TelegramModule;
import dev.neuralnexus.switchboard.modules.websocket.WebSocketModule;
import dev.neuralnexus.taterapi.Platform;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.event.api.ServerEvents;
import dev.neuralnexus.taterapi.logger.Logger;
import dev.neuralnexus.taterapi.metrics.bstats.BStatsMetrics;
import dev.neuralnexus.taterapi.metrics.bstats.MetricsAdapter;
import dev.neuralnexus.taterloader.Loader;
import dev.neuralnexus.taterloader.event.api.PluginEvents;
import dev.neuralnexus.taterloader.plugin.ModuleLoader;
import dev.neuralnexus.taterloader.plugin.Plugin;
import dev.neuralnexus.taterloader.plugin.impl.ModuleLoaderImpl;

import java.util.HashMap;

/** Main class for the plugin. */
public class Switchboard implements Plugin {
    public static final String PROJECT_NAME = "Switchboard";
    public static final String PROJECT_ID = "switchboard";
    public static final String PROJECT_VERSION = "1.0.4-SNAPSHOT";
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
        logger.info(
                Switchboard.PROJECT_NAME
                        + " is running on "
                        + TaterAPIProvider.platform()
                        + " "
                        + TaterAPIProvider.minecraftVersion()
                        + "!");
        PluginEvents.DISABLED.register(event -> onDisable());

        Loader loader = Loader.instance();

        // Set up bStats
        HashMap<Platform, Integer> statsMap = new HashMap<>();
        statsMap.put(Platform.BUKKIT, 21170);
        statsMap.put(Platform.BUNGEECORD, 21171);
        statsMap.put(Platform.SPONGE, 21172);
        statsMap.put(Platform.VELOCITY, 21173);
        metrics =
                MetricsAdapter.setupMetrics(
                        loader.plugin(), loader.server(), logger().getLogger(), statsMap);

        // Config
        SwitchboardConfigLoader.load();

        // Register API
        SwitchboardAPIProvider.register(new SwitchboardAPI());

        if (!RELOADED) {
            if (metrics != null) {
                ServerEvents.STOPPED.register(event -> metrics.shutdown());
            }

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
            if (SwitchboardConfigLoader.config().checkModule("websocket")) {
                moduleLoader.registerModule(new WebSocketModule());
            }
        }

        logger().info(PROJECT_NAME + " has been started!");
    }

    @Override
    public void onDisable() {
        // Remove references to objects
        SwitchboardConfigLoader.unload();

        // Unregister API
        SwitchboardAPIProvider.unregister();

        logger().info(PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public void reload() {
        RELOADED = true;

        // Stop
        onDisable();
        Loader.instance().pluginModuleLoader(PROJECT_ID).ifPresent(ModuleLoader::onDisable);

        // Start
        onEnable();
        Loader.instance().pluginModuleLoader(PROJECT_ID).ifPresent(ModuleLoader::onEnable);

        logger().info(PROJECT_NAME + " has been reloaded!");
    }
}
