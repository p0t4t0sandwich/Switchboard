package dev.neuralnexus.switchboard;

import com.google.common.collect.ImmutableMap;

import dev.neuralnexus.switchboard.api.SwitchboardAPI;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.DiscordModule;
import dev.neuralnexus.switchboard.modules.minecraft.MinecraftModule;
import dev.neuralnexus.switchboard.modules.proxy.ProxyModule;
import dev.neuralnexus.switchboard.modules.socket.SocketModule;
import dev.neuralnexus.switchboard.modules.telegram.TelegramModule;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.bstats.MetricsAdapter;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.ModuleLoader;

/** Main class for the plugin. */
public class Switchboard {
    private static final Switchboard instance = new Switchboard();
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private static ModuleLoader moduleLoader;
    private Object plugin;
    private Object pluginServer;
    private Object pluginLogger;
    private AbstractLogger logger;

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

    /**
     * Start
     *
     * @param plugin The plugin
     * @param pluginServer The plugin server
     * @param pluginLogger The plugin logger
     * @param logger The logger
     */
    public static void start(
            Object plugin, Object pluginServer, Object pluginLogger, AbstractLogger logger) {
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
            logger.info(Constants.PROJECT_NAME + " has already started!");
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
            if (SwitchboardConfigLoader.config().checkModule("socket")) {
                moduleLoader.registerModule(new SocketModule());
            }
        }

        // Start modules
        logger().info("Starting modules: " + moduleLoader.moduleNames());
        moduleLoader.startModules();

        logger().info(Constants.PROJECT_NAME + " has been started!");
    }

    /** Start */
    public static void start() {
        start(instance.plugin, instance.pluginServer, instance.pluginLogger, instance.logger);
    }

    /** Stop */
    public static void stop() {
        if (!STARTED) {
            logger().info(Constants.PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Stop modules
        logger().info("Stopping modules: " + moduleLoader.moduleNames());
        moduleLoader.stopModules();

        // Remove references to objects
        SwitchboardConfigLoader.unload();

        logger().info(Constants.PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public static void reload() {
        if (!STARTED) {
            logger().info(Constants.PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Unregister API
        SwitchboardAPIProvider.unregister();

        // Start
        start();

        logger().info(Constants.PROJECT_NAME + " has been reloaded!");
    }

    /** Constants used throughout the plugin. */
    public static class Constants {
        public static final String PROJECT_NAME = "Switchboard";
        public static final String PROJECT_ID = "switchboard";
        public static final String PROJECT_VERSION = "1.0.4-R0.2-SNAPSHOT";
        public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
        public static final String PROJECT_DESCRIPTION =
                "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.";
        public static final String PROJECT_URL = "https://github.com/p0t4t0sandwich/Switchboard";
    }
}
