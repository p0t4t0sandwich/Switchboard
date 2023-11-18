package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.tatercomms.common.api.TaterCommsAPI;
import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.modules.discord.command.DiscordCommand;
import dev.neuralnexus.tatercomms.common.commands.TaterCommsCommand;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.listeners.player.TaterCommsPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.TaterCommsServerListener;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.tatercomms.common.socket.Client;
import dev.neuralnexus.tatercomms.common.socket.Server;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.common.event.api.CommandEvents;
import dev.neuralnexus.taterlib.common.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.common.event.api.PluginMessageEvents;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;
import dev.neuralnexus.taterlib.common.logger.AbstractLogger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Main class for the TaterComms plugin.
 */
public class TaterComms {
    private static final TaterComms instance = new TaterComms();
    private Object plugin;
    private AbstractLogger logger;
    private static boolean STARTED = false;
    private static boolean RELOADED = false;
    private static DiscordBot discord = null;
    private static Server socketServer = null;
    private static Client socketClient = null;
    private static CommsRelay messageRelay = null;
    public static Supplier<Set<String>> proxyServers = HashSet::new;

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

        if (!RELOADED) {
            // Register commands
            CommandEvents.REGISTER_COMMAND.register((event -> event.registerCommand(TaterComms.getPlugin(), new TaterCommsCommand(), "tc")));

            // Register player listeners
            PlayerEvents.ADVANCEMENT_FINISHED.register(TaterCommsPlayerListener::onPlayerAdvancementFinished);
            PlayerEvents.DEATH.register(TaterCommsPlayerListener::onPlayerDeath);
            PlayerEvents.LOGIN.register(TaterCommsPlayerListener::onPlayerLogin);
            PlayerEvents.LOGOUT.register(TaterCommsPlayerListener::onPlayerLogout);
            PlayerEvents.MESSAGE.register(TaterCommsPlayerListener::onPlayerMessage);
            PlayerEvents.SERVER_SWITCH.register(TaterCommsPlayerListener::onPlayerServerSwitch);

            // Register server listeners
            ServerEvents.STARTED.register(TaterCommsServerListener::onServerStarted);
            ServerEvents.STOPPED.register(TaterCommsServerListener::onServerStopped);

            if (TaterCommsConfig.serverUsingProxy()) {
                // Register plugin channels
                TaterAPIProvider.get().registerChannels(CommsMessage.MessageType.getTypes());

                // Register plugin message listeners
                PluginMessageEvents.SERVER_PLUGIN_MESSAGE.register(CommsMessage::parseMessageChannel);
            }
        }

        // Start the socket server
        if (TaterCommsConfig.remoteEnabled()) {
            if (TaterCommsConfig.remotePrimary()) {
                socketServer = new Server(TaterCommsConfig.remotePort(), TaterCommsConfig.remoteSecret());
                Utils.runTaskAsync(socketServer::start);
            } else {
                socketClient = new Client(TaterCommsConfig.remoteHost(), TaterCommsConfig.remotePort(),  TaterCommsConfig.remoteSecret());
                Utils.runTaskAsync(socketClient::start);
            }
        }

        logger.info(Constants.PROJECT_NAME + " has been started!");

        TaterCommsAPIProvider.register(new TaterCommsAPI());
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

        // Remove references to objects
        TaterCommsConfig.unloadConfig();
        if (discord != null) {
            discord.removeListeners();
        }
        discord = null;
        if (socketServer != null) {
            socketServer.stop();
        }
        socketServer = null;
        socketClient = null;
        messageRelay = null;

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

        // Stop
        stop();

        // Start
        start();

        instance.logger.info(Constants.PROJECT_NAME + " has been reloaded!");
    }

    /**
     * Set the proxyServers consumer
     * @param proxyServers The registerChannels consumer
     */
    public static void setProxyServers(Supplier<Set<String>> proxyServers) {
        TaterComms.proxyServers = proxyServers;
    }

    /**
     * Get Message Relay
     */
    public static CommsRelay getMessageRelay() {
        return messageRelay;
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
