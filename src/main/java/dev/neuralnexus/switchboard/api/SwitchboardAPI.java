/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import dev.neuralnexus.switchboard.api.impl.SchedulerImpl;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.discord.api.DiscordAPI;
import dev.neuralnexus.switchboard.modules.telegram.api.TelegramAPI;
import dev.neuralnexus.switchboard.modules.webhook.api.WebhookAPI;
import dev.neuralnexus.switchboard.modules.websocket.api.WebSocketAPI;

/** API wrapper class */
public class SwitchboardAPI {
    private static Scheduler scheduler;
    private DiscordAPI discordAPI;
    private TelegramAPI telegramAPI;
    private WebhookAPI webhookAPI;
    private WebSocketAPI webSocketAPI;

    public SwitchboardAPI() {
        scheduler = new SchedulerImpl();
        if (SwitchboardConfigLoader.config().checkModule("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("telegram")) {
            this.telegramAPI = new TelegramAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("webhook")) {
            this.webhookAPI = new WebhookAPI();
        }
        if (SwitchboardConfigLoader.config().checkModule("websocket")) {
            this.webSocketAPI = new WebSocketAPI();
        }
    }

    /**
     * Get the Scheduler.
     *
     * @return The Scheduler.
     */
    public static Scheduler scheduler() {
        return scheduler;
    }

    /**
     * Get the Discord API.
     *
     * @return The Discord API.
     */
    public DiscordAPI discordAPI() {
        return this.discordAPI;
    }

    /**
     * Get the Telegram API.
     *
     * @return The Telegram API.
     */
    public TelegramAPI telegramAPI() {
        return this.telegramAPI;
    }

    /**
     * Get the Webhook API.
     *
     * @return The Webhook API.
     */
    public WebhookAPI webhookAPI() {
        return this.webhookAPI;
    }

    /**
     * Get the WebSocket API.
     *
     * @return The WebSocket API.
     */
    public WebSocketAPI webSocketAPI() {
        return this.webSocketAPI;
    }
}
