/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.api.DiscordAPI;
import dev.neuralnexus.switchboard.modules.telegram.api.TelegramAPI;
import dev.neuralnexus.switchboard.modules.websocket.api.WebSocketAPI;

/** API wrapper class */
public class SwitchboardAPI {
    private DiscordAPI discordAPI;
    private TelegramAPI telegramAPI;
    private WebSocketAPI webSocketAPI;

    public SwitchboardAPI() {
        if (SwitchboardConfigLoader.config().checkModule("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("telegram")) {
            this.telegramAPI = new TelegramAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("websocket")) {
            this.webSocketAPI = new WebSocketAPI();
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
     * Get the WebSocket API.
     *
     * @return The WebSocket API.
     */
    public WebSocketAPI webSocketAPI() {
        return webSocketAPI;
    }
}
