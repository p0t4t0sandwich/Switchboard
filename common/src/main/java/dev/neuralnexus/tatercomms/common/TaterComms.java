package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.listeners.player.CommonPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.CommonServerListener;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
import dev.neuralnexus.taterlib.common.event.server.ServerEvents;
import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.YamlDocument;
import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.block.Block;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Main class for the TaterComms plugin.
 */
public class TaterComms {
    private static final TaterComms instance = new TaterComms();
    private static String configPath;
    private static AbstractLogger logger;
    private static boolean STARTED = false;
    private static final ArrayList<Object> hooks = new ArrayList<>();
    private static DiscordBot discord = null;
    private static CommsRelay messageRelay = null;

    /**
     * Constructor for the TaterComms class.
     */
    public TaterComms() {}

    /**
     * Getter for the singleton instance of the TaterComms class.
     * @return The singleton instance
     */
    public static TaterComms getInstance() {
        return instance;
    }

    /**
     * Add a hook to the list of hooks
     * @param hook The hook to add
     */
    public static void addHook(Object hook) {
        hooks.add(hook);
    }

    /**
     * Use the Logger
     * @param message The message to log
     */
    public static void useLogger(String message) {
        logger.info(message);
    }

    /**
     * Start
     * @param configPath The path to the config file
     * @param logger The logger
     */
    public static void start(String configPath, AbstractLogger logger) {
        TaterComms.configPath = configPath;
        TaterComms.logger = logger;

        // Config
        TaterCommsConfig.loadConfig(configPath);

        if (STARTED) {
            useLogger("TaterComms has already started!");
            return;
        }
        STARTED = true;

        // Register player listeners
        PlayerEvents.ADVANCEMENT_FINISHED.register(CommonPlayerListener::onPlayerAdvancementFinished);
        PlayerEvents.DEATH.register(CommonPlayerListener::onPlayerDeath);
        PlayerEvents.LOGIN.register(CommonPlayerListener::onPlayerLogin);
        PlayerEvents.LOGOUT.register(CommonPlayerListener::onPlayerLogout);
        PlayerEvents.MESSAGE.register(CommonPlayerListener::onPlayerMessage);
        PlayerEvents.SERVER_SWITCH.register(CommonPlayerListener::onPlayerServerSwitch);

        // Register server listeners
        ServerEvents.STARTED.register(CommonServerListener::onServerStarted);
        ServerEvents.STOPPED.register(CommonServerListener::onServerStopped);

        // Get the Discord token from the config
        String discordToken = TaterCommsConfig.discordToken();
        if (discordToken == null || discordToken.isEmpty()) {
            useLogger("No Discord token found in tatercomms.config.yml!");
            TaterCommsConfig.setDiscordEnabled(false);
        }

        // Get server-channel mappings from the config
        HashMap<String, String> serverChannels = TaterCommsConfig.discordChannels();
        if (serverChannels.isEmpty()) {
            useLogger("No server-channel mappings found in tatercomms.config.yml!");
            TaterCommsConfig.setDiscordEnabled(false);
        }

        HashMap<String, String> formatting = TaterCommsConfig.formattingChat();
        if (TaterCommsConfig.discordEnabled()) {
            discord = new DiscordBot(discordToken, serverChannels);
        }
        messageRelay = new CommsRelay(formatting, discord);

        // Cancel chat and set message relay
        TaterLib.cancelChat = TaterCommsConfig.formattingEnabled();
        TaterLib.setMessageRelay(messageRelay);

        useLogger("TaterComms has been started!");
        TaterCommsAPIProvider.register(instance);
    }

    /**
     * Start
     */
    public static void start() {
        start(configPath, TaterLib.logger);
    }

    /**
     * Stop
     */
    public static void stop() {
        if (!STARTED) {
            useLogger("TaterComms has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        TaterCommsConfig.unloadConfig();
        discord = null;
        messageRelay = null;

        useLogger("TaterComms has been stopped!");
        TaterCommsAPIProvider.unregister();
    }

    /**
     * Reload
     */
    public static void reload() {
        if (!STARTED) {
            useLogger("TaterComms has not been started!");
            return;
        }

        // Stop
        stop();

        // Start
        start(configPath, logger);

        useLogger("TaterComms has been reloaded!");
    }
}
