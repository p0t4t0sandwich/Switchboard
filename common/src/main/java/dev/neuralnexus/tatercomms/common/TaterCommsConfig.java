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
     *
     * @param configPath The path to the config file
     */
    public static void loadConfig(String configPath) {
        try {
            config =
                    YamlDocument.create(
                            new File(
                                    "."
                                            + File.separator
                                            + configPath
                                            + File.separator
                                            + "TaterComms",
                                    "tatercomms.config.yml"),
                            Objects.requireNonNull(
                                    TaterComms.class
                                            .getClassLoader()
                                            .getResourceAsStream("tatercomms.config.yml")));
            config.reload();
        } catch (IOException | NullPointerException e) {
            TaterComms.getLogger()
                    .info(("Failed to load tatercomms.config.yml!\n" + e.getMessage()));
            e.printStackTrace();
        }
    }

    /** Unload the config */
    public static void unloadConfig() {
        config = null;
    }

    /** Save the config */
    public static void saveConfig() {
        try {
            config.save();
        } catch (IOException e) {
            TaterComms.getLogger().info("Failed to save tatercomms.config.yml!\n" + e.getMessage());
        }
    }

    /**
     * Check to see if a module is enabled
     *
     * @param module The module
     */
    public static boolean isModuleEnabled(String module) {
        return config.getBoolean("modules." + module + ".enabled");
    }

    /**
     * Get the server name from the config
     *
     * @return The server name
     */
    public static String name() {
        return config.getString("name");
    }

    /**
     * Get whether global chat is enabled by default from the config file
     *
     * @return Whether global chat is enabled by default
     */
    public static boolean globalChatEnabledByDefault() {
        return config.getBoolean("globalChatEnabledByDefault");
    }

    /**
     * Get weather chat formatting is enabled
     *
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
     *
     * @return The map of formatting
     */
    public static HashMap<String, String> formattingChat() {
        HashMap<String, String> formatting = new HashMap<>();
        HashMap<String, Block> formattingConfig =
                (HashMap<String, Block>) config.getBlock("formatting.chat").getStoredValue();
        for (Map.Entry<String, Block> entry : formattingConfig.entrySet()) {
            formatting.put(entry.getKey(), (String) entry.getValue().getStoredValue());
        }
        return formatting;
    }

    /** Discord config. */
    public static class DiscordConfig {
        /**
         * Get whether Discord chat is enabled
         *
         * @return Whether Discord chat is enabled
         */
        public static boolean enabled() {
            return config.getBoolean("modules.discord.enabled");
        }

        /**
         * Get the Discord token from the config file.
         *
         * @return The Discord token
         */
        public static String token() {
            return config.getString("modules.discord.token");
        }

        /** Get the Discord invite link */
        public static String inviteUrl() {
            return config.getString("modules.discord.inviteUrl");
        }

        /**
         * Get the Discord channel-server mappings from the config file
         *
         * @return The map of server channels
         */
        public static HashMap<String, String> channels() {
            HashMap<String, String> serverChannels = new HashMap<>();
            HashMap<String, Block> channelConfig =
                    (HashMap<String, Block>)
                            config.getBlock("modules.discord.channels").getStoredValue();
            for (Map.Entry<String, Block> entry : channelConfig.entrySet()) {
                serverChannels.put(entry.getKey(), (String) entry.getValue().getStoredValue());
            }
            return serverChannels;
        }
    }

    /** Minecraft config. */
    public static class MinecraftConfig {
        /**
         * Get whether Minecraft chat is enabled
         *
         * @return Whether Minecraft chat is enabled
         */
        public static boolean enabled() {
            return config.getBoolean("modules.minecraft.enabled");
        }
    }

    /** Proxy config. */
    public static class ProxyConfig {
        /**
         * Get whether proxy chat is enabled
         *
         * @return Whether proxy chat is enabled
         */
        public static boolean enabled() {
            return config.getBoolean("modules.proxy.enabled");
        }
    }

    /** Socket config. */
    public static class SocketConfig {
        /**
         * Get whether remote chat is enabled
         *
         * @return Whether remote chat is enabled
         */
        public static boolean enabled() {
            return config.getBoolean("modules.socket.enabled");
        }

        /**
         * Get whether the server is the primary server
         *
         * @return Whether the server is the primary server
         */
        public static boolean primary() {
            return config.getBoolean("modules.socket.primary");
        }

        /**
         * Get the remote host
         *
         * @return The remote host
         */
        public static String host() {
            return config.getString("modules.socket.host");
        }

        /**
         * Get the remote port
         *
         * @return The remote port
         */
        public static int port() {
            return config.getInt("modules.socket.port");
        }

        /**
         * Get the remote secret
         *
         * @return The remote secret
         */
        public static String secret() {
            String secret = config.getString("modules.socket.secret");
            if (secret == null || secret.isEmpty()) {
                TaterComms.getLogger().info("Generating new remote secret");
                secret = UUID.randomUUID().toString();
                config.set("modules.socket.secret", secret);
                saveConfig();
            }
            return secret;
        }
    }
}
