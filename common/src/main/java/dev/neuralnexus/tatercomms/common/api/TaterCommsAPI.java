package dev.neuralnexus.tatercomms.common.api;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.modules.discord.api.DiscordAPI;
import dev.neuralnexus.tatercomms.common.modules.socket.api.SocketAPI;

import java.util.Optional;

/**
 * API wrapper class
 */
public class TaterCommsAPI {
    private final Data data;
    private DiscordAPI discordAPI = null;
    private SocketAPI socketAPI = null;

    public TaterCommsAPI() {
        this.data = new Data();
        if (TaterCommsConfig.isModuleEnabled("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (TaterCommsConfig.isModuleEnabled("socket")) {
            this.socketAPI = new SocketAPI();
        }
    }

    /**
     * Get the Discord API.
     * @return The Discord API.
     */
    public DiscordAPI discordAPI() {
        return discordAPI;
    }

    /**
     * Get the Socket API.
     * @return The Socket API.
     */
    public SocketAPI socketAPI() {
        return socketAPI;
    }

    /**
     * The data for the API.
     */
    static class Data {}
}
