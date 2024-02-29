package dev.neuralnexus.switchboard.api;

import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.api.DiscordAPI;
import dev.neuralnexus.switchboard.modules.socket.api.SocketAPI;
import dev.neuralnexus.switchboard.modules.telegram.api.TelegramAPI;

/** API wrapper class */
public class SwitchboardAPI {
    private DiscordAPI discordAPI;
    private TelegramAPI telegramAPI;
    private SocketAPI socketAPI;

    public SwitchboardAPI() {
        if (SwitchboardConfigLoader.config().checkModule("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("telegram")) {
            this.telegramAPI = new TelegramAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("socket")) {
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
     * Get the Telegram API.
     *
     * @return The Telegram API.
     */
    public TelegramAPI telegramAPI() {
        return telegramAPI;
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
