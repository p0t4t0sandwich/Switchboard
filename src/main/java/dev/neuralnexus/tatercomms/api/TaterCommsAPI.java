package dev.neuralnexus.tatercomms.api;

import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.modules.discord.api.DiscordAPI;
import dev.neuralnexus.tatercomms.modules.socket.api.SocketAPI;

/** API wrapper class */
public class TaterCommsAPI {
    private DiscordAPI discordAPI;
    private SocketAPI socketAPI;

    public TaterCommsAPI() {
        if (TaterCommsConfigLoader.config().checkModule("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (TaterCommsConfigLoader.config().checkModule("socket")) {
            this.socketAPI = new SocketAPI();
        }
    }

    /**
     * Get the Discord API.
     *
     * @return The Discord API.
     */
    public DiscordAPI discordAPI() {
        return discordAPI;
    }

    /**
     * Get the Socket API.
     *
     * @return The Socket API.
     */
    public SocketAPI socketAPI() {
        return socketAPI;
    }
}
