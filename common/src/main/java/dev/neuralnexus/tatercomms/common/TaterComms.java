package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.tatercomms.common.api.TaterCommsAPI;
import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.modules.discord.DiscordModule;
import dev.neuralnexus.tatercomms.common.modules.minecraft.MinecraftModule;
import dev.neuralnexus.tatercomms.common.modules.socket.SocketModule;
import dev.neuralnexus.taterlib.common.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.common.logger.AbstractLogger;

/**
 * Main class for the plugin.
 */
public class TaterComms {
    private static final TaterComms instance = new TaterComms();
    private Object plugin;
    private AbstractLogger logger;
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    /**
     * Getter for the singleton instance of the class.
     * @return The singleton instance
     */
    public static TaterComms getInstance() {
        return instance;
    }

    /**
     * Set the plugin
     * @param plugin The plugin
     */
    private static void setPlugin(Object plugin) {
        instance.plugin = plugin;
    }

    /**
     * Get the plugin
     * @return The plugin
     */
    public static Object getPlugin() {
        return instance.plugin;
    }

    /**
     * Set the logger
     * @param logger The logger
     */
    private static void setLogger(AbstractLogger logger) {
        instance.logger = logger;
    }

    /**
     * Get the logger
     * @return The logger
     */
    public static AbstractLogger getLogger() {
        return instance.logger;
    }

    /**
     * Start
     * @param plugin The plugin
     * @param logger The logger
     */
    public static void start(Object plugin, AbstractLogger logger) {
        setPlugin(plugin);
        setLogger(logger);

        // Config
        TaterCommsConfig.loadConfig(TaterAPIProvider.get().configFolder());

        if (STARTED) {
            logger.info(Constants.PROJECT_NAME + " has already started!");
            return;
        }
        STARTED = true;

        // Register API
        TaterCommsAPIProvider.register(new TaterCommsAPI());

        // Register modules
        if (TaterCommsConfig.isModuleEnabled("minecraft")) {
            TaterCommsModuleLoader.registerModule(new MinecraftModule());
        }
        if (TaterCommsConfig.isModuleEnabled("discord")) {
            TaterCommsModuleLoader.registerModule(new DiscordModule());
        }
        if (TaterCommsConfig.isModuleEnabled("socket")) {
            TaterCommsModuleLoader.registerModule(new SocketModule());
        }

        // Start modules
        TaterCommsModuleLoader.startModules();

        logger.info(Constants.PROJECT_NAME + " has been started!");
    }

    /**
     * Start
     */
    public static void start() {
        start(instance.plugin, instance.logger);
    }

    /**
     * Stop
     */
    public static void stop() {
        if (!STARTED) {
            instance.logger.info(Constants.PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;

        // Stop modules
        TaterCommsModuleLoader.stopModules();

        // Remove references to objects
        TaterCommsConfig.unloadConfig();

        instance.logger.info("TaterComms has been stopped!");
        TaterCommsAPIProvider.unregister();
    }

    /**
     * Reload
     */
    public static void reload() {
        if (!STARTED) {
            instance.logger.info(Constants.PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Reload config
        TaterCommsConfig.unloadConfig();
        TaterCommsConfig.loadConfig(TaterAPIProvider.get().configFolder());

        // Reload modules
        TaterCommsModuleLoader.reloadModules();

        instance.logger.info(Constants.PROJECT_NAME + " has been reloaded!");
    }

    /**
     * Constants used throughout the plugin.
     */
    public static class Constants {
        public static final String PROJECT_NAME = "TaterComms";
        public static final String PROJECT_ID = "tatercomms";
        public static final String PROJECT_VERSION = "1.0.4-R0.1-SNAPSHOT";
        public static final String PROJECT_AUTHORS = "p0t4t0sandwich";
        public static final String PROJECT_DESCRIPTION = "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.";
        public static final String PROJECT_URL = "https://github.com/p0t4t0sandwich/TaterComms";
    }
}
