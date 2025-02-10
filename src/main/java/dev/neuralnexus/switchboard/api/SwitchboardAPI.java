/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import dev.neuralnexus.switchboard.api.impl.SchedulerImpl;
import dev.neuralnexus.switchboard.api.impl.discord.DiscordAPI;
import dev.neuralnexus.switchboard.api.impl.telegram.TelegramAPI;
import dev.neuralnexus.switchboard.api.impl.webhook.WebhookAPI;
import dev.neuralnexus.switchboard.api.impl.websocket.WebSocketAPI;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;

import org.jetbrains.annotations.ApiStatus;

/** API wrapper class */
public class SwitchboardAPI {
    private static SwitchboardAPI instance;
    private static Scheduler scheduler;
    private DiscordAPI discordAPI;
    private TelegramAPI telegramAPI;
    private WebhookAPI webhookAPI;
    private WebSocketAPI webSocketAPI;

    /**
     * Get the instance of the API.
     *
     * @return The instance of the API.
     */
    public static SwitchboardAPI instance() {
        if (instance == null) {
            throw new IllegalStateException("Switchboard API has not been registered!");
        }
        return instance;
    }

    /**
     * Unregister the API<br>
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    @ApiStatus.Internal
    public static void unregister() {
        SwitchboardAPI.scheduler().shutdownBackgroundScheduler();
        scheduler = null;
        if (instance.discordAPI != null) {
            instance.discordAPI.removeBot();
            instance.discordAPI = null;
        }
        if (instance.telegramAPI != null) {
            instance.telegramAPI.removeBot();
            instance.telegramAPI = null;
        }
        if (instance.webSocketAPI != null) {
            instance.webSocketAPI.stopWebSocket();
            instance.webSocketAPI = null;
        }
        instance = null;
    }

    public SwitchboardAPI() {
        instance = this;
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
