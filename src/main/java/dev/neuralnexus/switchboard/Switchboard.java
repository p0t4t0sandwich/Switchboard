/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard;

import com.google.common.collect.ImmutableMap;

import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.DiscordModule;
import dev.neuralnexus.switchboard.modules.minecraft.MinecraftModule;
import dev.neuralnexus.switchboard.modules.proxy.ProxyModule;
import dev.neuralnexus.switchboard.modules.telegram.TelegramModule;
import dev.neuralnexus.switchboard.modules.websocket.WebSocketModule;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.bstats.MetricsAdapter;
import dev.neuralnexus.taterlib.event.api.PluginEvents;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.ModuleLoader;
import dev.neuralnexus.taterlib.plugin.Plugin;

/** Main class for the plugin. */
public class Switchboard implements Plugin {
    public static final String PROJECT_NAME = "Switchboard";
    public static final String PROJECT_ID = "switchboard";
    public static final String PROJECT_VERSION = "1.0.4-R0.2-SNAPSHOT";
    public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
    public static final String PROJECT_DESCRIPTION =
            "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.";
    public static final String PROJECT_URL = "https://github.com/p0t4t0sandwich/Switchboard";

    private static final Switchboard instance = new Switchboard();
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private static ModuleLoader moduleLoader;
    private Object plugin;
    private Object pluginServer;
    private Object pluginLogger;
    private AbstractLogger logger;

    @Override
    public String name() {
        return Switchboard.PROJECT_NAME;
    }

    @Override
    public String id() {
        return Switchboard.PROJECT_ID;
    }

    @Override
    public void pluginStart(
            Object plugin, Object pluginServer, Object pluginLogger, AbstractLogger logger) {
        logger.info(
                Switchboard.PROJECT_NAME
                        + " is running on "
                        + TaterAPIProvider.serverType()
                        + " "
                        + TaterAPIProvider.minecraftVersion()
                        + "!");
        PluginEvents.DISABLED.register(event -> pluginStop());

        if (pluginServer != null) {
            setPluginServer(pluginServer);
        }
        if (pluginLogger != null) {
            setPluginLogger(pluginLogger);
        }
        setPlugin(plugin);
        setLogger(logger);

        // Set up bStats
        MetricsAdapter.setupMetrics(
                plugin,
                pluginServer,
                pluginLogger,
                ImmutableMap.<ServerType, Integer>builder()
                        .put(ServerType.BUKKIT, 21170)
                        .put(ServerType.BUNGEECORD, 21171)
                        .put(ServerType.SPONGE, 21172)
                        .put(ServerType.VELOCITY, 21172)
                        .build());

        if (STARTED) {
            logger.info(PROJECT_NAME + " has already started!");
            return;
        }
        STARTED = true;

        // Config
        SwitchboardConfigLoader.load();

        // Register API
        SwitchboardAPIProvider.register(new SwitchboardAPI());

        if (!RELOADED) {
            // Register modules
            moduleLoader = new SwitchboardModuleLoader();
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

        // Start modules
        logger().info("Starting modules: " + moduleLoader.moduleNames());
        moduleLoader.startModules();

        logger().info(PROJECT_NAME + " has been started!");
    }

    @Override
    public void pluginStop() {
        if (!STARTED) {
            logger().info(PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Stop modules
        logger().info("Stopping modules: " + moduleLoader.moduleNames());
        moduleLoader.stopModules();

        // Remove references to objects
        SwitchboardConfigLoader.unload();

        // Unregister API
        SwitchboardAPIProvider.unregister();

        logger().info(PROJECT_NAME + " has been stopped!");
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

    /**
     * Get the plugin
     *
     * @return The plugin
     */
    public static Object plugin() {
        return instance.plugin;
    }

    /**
     * Set the plugin
     *
     * @param plugin The plugin
     */
    private static void setPlugin(Object plugin) {
        instance.plugin = plugin;
    }

    /**
     * Set the plugin server
     *
     * @param pluginServer The plugin server
     */
    private static void setPluginServer(Object pluginServer) {
        instance.pluginServer = pluginServer;
    }

    /**
     * Set the plugin logger
     *
     * @param pluginLogger The plugin logger
     */
    private static void setPluginLogger(Object pluginLogger) {
        instance.pluginLogger = pluginLogger;
    }

    /**
     * Get the logger
     *
     * @return The logger
     */
    public static AbstractLogger logger() {
        return instance.logger;
    }

    /**
     * Set the logger
     *
     * @param logger The logger
     */
    private static void setLogger(AbstractLogger logger) {
        instance.logger = logger;
    }

    /** Reload */
    public void reload() {
        if (!STARTED) {
            logger().info(PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        pluginStop();

        // Start
        pluginStart(instance.plugin, instance.pluginServer, instance.pluginLogger, instance.logger);

        logger().info(PROJECT_NAME + " has been reloaded!");
    }
}
