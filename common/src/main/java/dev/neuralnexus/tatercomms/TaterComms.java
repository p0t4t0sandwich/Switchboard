package dev.neuralnexus.tatercomms;

import dev.neuralnexus.tatercomms.api.TaterCommsAPI;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.modules.discord.DiscordModule;
import dev.neuralnexus.tatercomms.modules.minecraft.MinecraftModule;
import dev.neuralnexus.tatercomms.modules.proxy.ProxyModule;
import dev.neuralnexus.tatercomms.modules.socket.SocketModule;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.logger.AbstractLogger;

/** Main class for the plugin. */
public class TaterComms {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private static Object plugin;
    private static AbstractLogger logger;

    /**
     * Get the plugin
     *
     * @return The plugin
     */
    public static Object getPlugin() {
        return plugin;
    }

    /**
     * Set the plugin
     *
     * @param plugin The plugin
     */
    private static void setPlugin(Object plugin) {
        TaterComms.plugin = plugin;
    }

    /**
     * Get the logger
     *
     * @return The logger
     */
    public static AbstractLogger getLogger() {
        return logger;
    }

    /**
     * Set the logger
     *
     * @param logger The logger
     */
    private static void setLogger(AbstractLogger logger) {
        TaterComms.logger = logger;
    }

    /**
     * Start
     *
     * @param plugin The plugin
     * @param logger The logger
     */
    public static void start(Object plugin, AbstractLogger logger) {
        setPlugin(plugin);
        setLogger(logger);

        if (STARTED) {
            logger.info(Constants.PROJECT_NAME + " has already started!");
            return;
        }
        STARTED = true;

        // Config
        TaterCommsConfig.loadConfig(TaterAPIProvider.get().configFolder());

        // Register API
        TaterCommsAPIProvider.register(new TaterCommsAPI());
        TaterCommsAPI api = TaterCommsAPIProvider.get();
        api.setServerName(TaterCommsConfig.name());
        api.setFormatting(TaterCommsConfig.formattingChat());
        api.setUsingProxy(TaterCommsConfig.ProxyConfig.enabled());

        if (!RELOADED) {
            // Register modules
            if (TaterCommsConfig.isModuleEnabled("minecraft")) {
                TaterCommsModuleLoader.registerModule(new MinecraftModule());
            }
            if (TaterCommsConfig.isModuleEnabled("discord")) {
                TaterCommsModuleLoader.registerModule(new DiscordModule());
            }
            if (TaterCommsConfig.isModuleEnabled("proxy")) {
                TaterCommsModuleLoader.registerModule(new ProxyModule());
            }
            if (TaterCommsConfig.isModuleEnabled("socket")) {
                TaterCommsModuleLoader.registerModule(new SocketModule());
            }
        }

        // Start modules
        TaterCommsModuleLoader.startModules();

        logger.info(Constants.PROJECT_NAME + " has been started!");
    }

    /** Stop */
    public static void stop() {
        if (!STARTED) {
            logger.info(Constants.PROJECT_NAME + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Stop modules
        TaterCommsModuleLoader.stopModules();

        // Remove references to objects
        TaterCommsConfig.unloadConfig();

        logger.info(Constants.PROJECT_NAME + " has been stopped!");
    }

    /** Reload */
    public static void reload() {
        if (!STARTED) {
            logger.info(Constants.PROJECT_NAME + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Unregister API
        TaterCommsAPIProvider.unregister();

        // Start
        start(plugin, logger);

        logger.info(Constants.PROJECT_NAME + " has been reloaded!");
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
