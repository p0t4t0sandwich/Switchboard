package dev.neuralnexus.switchboard.config.sections.discord;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Optional;
import java.util.Set;

/** A class for Discord configuration. */
@ConfigSerializable
public class DiscordConfig {
    @Setting private String token;
    @Setting private String inviteUrl;
    @Setting private Set<ChannelMapping> mappings;

    /**
     * Get the token.
     *
     * @return The token
     */
    public String token() {
        return token;
    }

    /**
     * Get the invite URL.
     *
     * @return The invite URL
     */
    public String inviteUrl() {
        return inviteUrl;
    }

    /**
     * Get the mappings.
     *
     * @return The mappings
     */
    public Set<ChannelMapping> mappings() {
        return mappings;
    }

    /**
     * Get the channel mappings for a server.
     *
     * @param server The server name
     * @return The channel mappings
     */
    public Optional<ChannelMapping> getMappings(String server) {
        for (ChannelMapping mapping : mappings) {
            if (mapping.server().equals(server)) {
                return Optional.of(mapping);
            }
        }
        return Optional.empty();
    }

    /**
     * Find a server name by guildId and channelId.
     *
     * @param guildId The guild id
     * @param channelId The channel id
     * @return The server name
     */
    public Optional<String> findServer(String guildId, String channelId) {
        for (ChannelMapping mapping : mappings) {
            for (ChannelMapping.DiscordChannel channel : mapping.channels()) {
                if (channel.guildId().equals(guildId) && channel.channelId().equals(channelId)) {
                    return Optional.of(mapping.server());
                }
            }
        }
        return Optional.empty();
    }
}
