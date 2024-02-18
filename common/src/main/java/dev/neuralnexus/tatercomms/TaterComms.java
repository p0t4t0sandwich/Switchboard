package dev.neuralnexus.tatercomms;

import dev.neuralnexus.tatercomms.api.TaterCommsAPI;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.modules.discord.DiscordModule;
import dev.neuralnexus.tatercomms.modules.minecraft.MinecraftModule;
import dev.neuralnexus.tatercomms.modules.proxy.ProxyModule;
import dev.neuralnexus.tatercomms.modules.socket.SocketModule;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.ModuleLoader;

/** Main class for the plugin. */
public class TaterComms {
    private static final TaterComms instance = new TaterComms();
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private static ModuleLoader moduleLoader;
    private Object plugin;
    private Object pluginServer;
    private Object pluginLogger;
    private AbstractLogger logger;

    /**
     * Getter for the singleton instance of the class.
     *
     * @return The singleton instance
     */
    public static TaterComms instance() {
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
        //        MetricsAdapter.setupMetrics(
        //                plugin,
        //                pluginServer,
        //                pluginLogger,
        //                ImmutableMap.<ServerType, Integer>builder()
        //                        .put(ServerType.BUKKIT, 21008)
        //                        .put(ServerType.BUNGEECORD, 21009)
        //                        .put(ServerType.SPONGE, 21010)
        //                        .put(ServerType.VELOCITY, 21011)
        //                        .build());

        if (STARTED) {
            logger.info(Constants.PROJECT_NAME + " has already started!");
            return;
        }
        STARTED = true;

        // Config
        TaterCommsConfig.loadConfig(TaterAPIProvider.serverType().dataFolders().configFolder());

        // Register API
        TaterCommsAPIProvider.register(new TaterCommsAPI());
        TaterCommsAPI api = TaterCommsAPIProvider.get();
        api.setServerName(TaterCommsConfig.name());
        api.setFormatting(TaterCommsConfig.formattingChat());
        api.setUsingProxy(TaterCommsConfig.ProxyConfig.enabled());

        if (!RELOADED) {
            // Register modules
            moduleLoader = new TaterCommsModuleLoader();
            if (TaterCommsConfig.isModuleEnabled("minecraft")) {
                moduleLoader.registerModule(new MinecraftModule());
            }
            if (TaterCommsConfig.isModuleEnabled("discord")) {
                moduleLoader.registerModule(new DiscordModule());
            }
            if (TaterCommsConfig.isModuleEnabled("proxy")) {
                moduleLoader.registerModule(new ProxyModule());
            }
            if (TaterCommsConfig.isModuleEnabled("socket")) {
                moduleLoader.registerModule(new SocketModule());
            }
        }

        // Start modules
        moduleLoader.startModules();

        logger.info(Constants.PROJECT_NAME + " has been started!");
    }

    /** Start */
    public static void start() {
        start(instance.plugin, instance.pluginServer, instance.pluginLogger, instance.logger);
    }

    /** Stop */
    public static void stop() {
        if (!STARTED) {
            instance.logger.info(Constants.PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Stop modules
        moduleLoader.stopModules();

        // Remove references to objects
        TaterCommsConfig.unloadConfig();

        instance.logger.info(Constants.PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public static void reload() {
        if (!STARTED) {
            instance.logger.info(Constants.PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Unregister API
        TaterCommsAPIProvider.unregister();

        // Start
        start();

        instance.logger.info(Constants.PROJECT_NAME + " has been reloaded!");
    }

    /** Constants used throughout the plugin. */
    public static class Constants {
        public static final String PROJECT_NAME = "TaterComms";
        public static final String PROJECT_ID = "tatercomms";
        public static final String PROJECT_VERSION = "1.0.4-R0.1-SNAPSHOT";
        public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
        public static final String PROJECT_DESCRIPTION =
                "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.";
        public static final String PROJECT_URL = "https://github.com/p0t4t0sandwich/TaterComms";
    }
}
