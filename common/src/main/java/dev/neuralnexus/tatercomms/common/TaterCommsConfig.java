package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.YamlDocument;
import dev.neuralnexus.taterlib.lib.dejvokep.boostedyaml.block.Block;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TaterCommsConfig {
    private static YamlDocument config;

    public TaterCommsConfig() {}

    /**
     * Load the config
     * @param configPath The path to the config file
     */
    public static void loadConfig(String configPath) {
        try {
            config = YamlDocument.create(new File("." + File.separator + configPath + File.separator + "TaterComms", "tatercomms.config.yml"),
                    Objects.requireNonNull(TaterComms.class.getClassLoader().getResourceAsStream("tatercomms.config.yml"))
            );
            config.reload();
        } catch (IOException | NullPointerException e) {
            TaterComms.getLogger().info(("Failed to load tatercomms.config.yml!\n" + e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Unload the config
     */
    public static void unloadConfig() {
        config = null;
    }

    /**
     * Save the config
     */
    public static void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            TaterComms.getLogger().info("Failed to save tatercomms.config.yml!\n" + e.getMessage());
        }
    }

    /**
     * Check to see if a module is enabled
     * @param module The module
     */
    public static boolean isModuleEnabled(String module) {
        return config.getBoolean("modules" + module + ".enabled");
    }

    /**
     * Discord config.
     */
    public static class DiscordConfig {
        /**
         * Get the Discord token from the config file.
         * @return The Discord token
         */
        public static String token() {
            return config.getString("modules.discord.token");
        }

        /**
         * Get the Discord invite link
         */
        public static String inviteUrl() {
            return config.getString("modules.discord.inviteUrl");
        }

        /**
         * Get the Discord channel-server mappings from the config file
         * @return The map of server channels
         */
        public static HashMap<String, String> channels() {
            HashMap<String, String> serverChannels = new HashMap<>();
            HashMap<String, Block> channelConfig = (HashMap<String, Block>) config.getBlock("modules.discord.channels").getStoredValue();
            for (Map.Entry<String, Block> entry: channelConfig.entrySet()) {
                serverChannels.put(entry.getKey(), (String) entry.getValue().getStoredValue());
            }
            return serverChannels;
        }
    }

    // TODO: Refactor
    // ------------------------- Deprecated -------------------------
    /**
     * Get the server name from the config
     * @return The server name
     */
    public static String serverName() {
        return config.getString("server.name");
    }

    /**
     * Get whether the server is using a proxy from the config file
     * @return Whether the server is using a proxy
     */
    public static boolean serverUsingProxy() {
        return config.getBoolean("server.usingProxy");
    }

    /**
     * Get whether global chat is enabled by default from the config file
     * @return Whether global chat is enabled by default
     */
    public static boolean serverGlobalChatEnabledByDefault() {
        return config.getBoolean("server.globalChatEnabledByDefault");
    }

    /**
     * Get weather Discord chat is enabled
     * @return Whether Discord chat is enabled
     */
    public static boolean discordEnabled() {
        return config.getBoolean("discord.enabled");
    }

    /**
     * Set whether Discord chat is enabled
     * @param enabled Whether Discord chat is enabled
     */
    public static void setDiscordEnabled(boolean enabled) {
        config.set("discord.enabled", enabled);
    }

    /**
     * Get the Discord token from the config file.
     * @return The Discord token
     */
    public static String discordToken() {
        return config.getString("discord.token");
    }

    /**
     * Get the Discord invite link
     */
    public static String discordInviteUrl() {
        return config.getString("discord.inviteUrl");
    }

    /**
     * Get the Discord channel-server mappings from the config file
     * @return The map of server channels
     */
    public static HashMap<String, String> discordChannels() {
        HashMap<String, String> serverChannels = new HashMap<>();
        HashMap<String, Block> channelConfig = (HashMap<String, Block>) config.getBlock("discord.channels").getStoredValue();
        for (Map.Entry<String, Block> entry: channelConfig.entrySet()) {
            serverChannels.put(entry.getKey(), (String) entry.getValue().getStoredValue());
        }
        return serverChannels;
    }

    /**
     * Get weather chat formatting is enabled
     * @return Whether chat formatting is enabled
     */
    public static boolean formattingEnabled() {
        return config.getBoolean("formatting.enabled");
    }

    public static Set<String> formattingPassthrough() {
        return new HashSet<>(config.getStringList("formatting.passthrough"));
    }

    /**
     * Get the formatting from the config file
     * @return The map of formatting
     */
    public static HashMap<String, String> formattingChat() {
        HashMap<String, String> formatting = new HashMap<>();
        HashMap<String, Block> formattingConfig = (HashMap<String, Block>) config.getBlock("formatting.chat").getStoredValue();
        for (Map.Entry<String, Block> entry: formattingConfig.entrySet()) {
            formatting.put(entry.getKey(), (String) entry.getValue().getStoredValue());
        }
        return formatting;
    }

    /**
     * Get whether remote chat is enabled
     * @return Whether remote chat is enabled
     */
    public static boolean remoteEnabled() {
        return config.getBoolean("remote.enabled");
    }

    /**
     * Get whether the server is the primary server
     * @return Whether the server is the primary server
     */
    public static boolean remotePrimary() {
        return config.getBoolean("remote.primary");
    }

    /**
     * Get the remote host
     * @return The remote host
     */
    public static String remoteHost() {
        return config.getString("remote.host");
    }

    /**
     * Get the remote port
     * @return The remote port
     */
    public static int remotePort() {
        return config.getInt("remote.port");
    }

    /**
     * Get the remote secret
     * @return The remote secret
     */
    public static String remoteSecret() {
        String secret = config.getString("remote.secret");
        if (secret == null || secret.isEmpty()) {
            TaterComms.getLogger().info("Generating new remote secret");
            secret = UUID.randomUUID().toString();
            config.set("remote.secret", secret);
            saveConfig();
        }
        return secret;
    }
}
