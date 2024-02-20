package dev.neuralnexus.tatercomms.config.sections.discord;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Set;

/** A class for Discord channel mappings. */
@ConfigSerializable
public class ChannelMapping {
    @Setting("serverName")
    private String serverName;

    @Setting private Set<DiscordChannel> channels;

    /**
     * Get the server name.
     *
     * @return The server name
     */
    public String server() {
        return serverName;
    }

    /**
     * Get the channels.
     *
     * @return The channels
     */
    public Set<DiscordChannel> channels() {
        return channels;
    }

    /** The channel guild/channel ids. */
    @ConfigSerializable
    public static class DiscordChannel {
        @Setting("guildId")
        private String guildId;

        @Setting("channelId")
        private String channelId;

        /**
         * Get the channel id.
         *
         * @return The channel id
         */
        public String guildId() {
            return guildId;
        }

        /**
         * Get the channel id.
         *
         * @return The channel id
         */
        public String channelId() {
            return channelId;
        }
    }
}
