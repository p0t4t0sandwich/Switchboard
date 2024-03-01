/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

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
