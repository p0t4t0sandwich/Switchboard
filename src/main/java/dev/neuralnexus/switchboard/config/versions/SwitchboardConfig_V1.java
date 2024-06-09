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
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import java.util.List;

/** A class for Switchboard configuration. */
public class SwitchboardConfig_V1 implements SwitchboardConfig {
    private final int version;
    private final List<ModuleConfig> modules;
    private final DiscordConfig discord;
    private final TelegramConfig telegram;
    private final WebSocketConfig webSocket;
    private final FormattingConfig formatting;

    public SwitchboardConfig_V1(
            int version,
            List<ModuleConfig> modules,
            DiscordConfig discord,
            TelegramConfig telegram,
            FormattingConfig formatting,
            WebSocketConfig webSocket) {
        this.version = version;
        this.modules = modules;
        this.discord = discord;
        this.telegram = telegram;
        this.formatting = formatting;
        this.webSocket = webSocket;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public List<ModuleConfig> modules() {
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
    public WebSocketConfig webSocket() {
        return webSocket;
    }

    @Override
    public FormattingConfig formatting() {
        return formatting;
    }
}
