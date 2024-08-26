/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.SwitchboardConfig;
import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.switchboard.config.sections.webhook.WebhookConfig;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;
import dev.neuralnexus.taterapi.config.ToggleableSetting;

import java.util.List;

/** A class for Switchboard configuration. */
public class SwitchboardConfig_V1 implements SwitchboardConfig {
    private final int version;
    private final List<ToggleableSetting> modules;
    private final DiscordConfig discord;
    private final TelegramConfig telegram;
    private final WebhookConfig webhook;
    private final WebSocketConfig webSocket;
    private final FormattingConfig formatting;

    public SwitchboardConfig_V1(
            int version,
            List<ToggleableSetting> modules,
            DiscordConfig discord,
            TelegramConfig telegram,
            FormattingConfig formatting,
            WebhookConfig webhook,
            WebSocketConfig webSocket) {
        this.version = version;
        this.modules = modules;
        this.discord = discord;
        this.telegram = telegram;
        this.formatting = formatting;
        this.webhook = webhook;
        this.webSocket = webSocket;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public List<ToggleableSetting> modules() {
        return modules;
    }

    @Override
    public DiscordConfig discord() {
        return discord;
    }

    @Override
    public TelegramConfig telegram() {
        return telegram;
    }

    @Override
    public WebhookConfig webhook() {
        return this.webhook;
    }

    @Override
    public WebSocketConfig webSocket() {
        return webSocket;
    }

    @Override
    public FormattingConfig formatting() {
        return formatting;
    }
}
