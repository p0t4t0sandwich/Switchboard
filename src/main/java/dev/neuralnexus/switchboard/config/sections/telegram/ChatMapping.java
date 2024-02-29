package dev.neuralnexus.switchboard.config.sections.telegram;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Set;

/** Telegram chat mapping. */
@ConfigSerializable
public class ChatMapping {
    @Setting("serverName")
    private String serverName;

    @Setting private Set<ChatChannel> channels;

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
    public Set<ChatChannel> channels() {
        return channels;
    }
}
