/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.SwitchboardConfig;
import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.socket.SocketConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import java.util.Set;

/** A class for Switchboard configuration. */
public class SwitchboardConfig_V1 implements SwitchboardConfig {
    private final int version;
    private final Set<ModuleConfig> modules;
    private final DiscordConfig discord;
    private final TelegramConfig telegram;
    private final SocketConfig socket;
    private final FormattingConfig formatting;

    public SwitchboardConfig_V1(
            int version,
            Set<ModuleConfig> modules,
            DiscordConfig discord,
            TelegramConfig telegram,
            FormattingConfig formatting,
            SocketConfig socket) {
        this.version = version;
        this.modules = modules;
        this.discord = discord;
        this.telegram = telegram;
        this.formatting = formatting;
        this.socket = socket;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public Set<ModuleConfig> modules() {
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
    public SocketConfig socket() {
        return socket;
    }

    @Override
    public FormattingConfig formatting() {
        return formatting;
    }
}
