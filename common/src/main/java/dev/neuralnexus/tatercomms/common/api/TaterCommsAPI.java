package dev.neuralnexus.tatercomms.common.api;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.modules.discord.api.DiscordAPI;

import java.util.Optional;

/**
 * API wrapper class
 */
public class TaterCommsAPI {
    private final Data data;
    private DiscordAPI discordAPI = null;

    public TaterCommsAPI() {
        this.data = new Data();
        if (TaterCommsConfig.isModuleEnabled("discord")) {
            this.discordAPI = new DiscordAPI();
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
     * The data for the API.
     */
    static class Data {}
}
