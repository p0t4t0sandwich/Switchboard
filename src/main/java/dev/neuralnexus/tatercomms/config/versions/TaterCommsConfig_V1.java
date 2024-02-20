package dev.neuralnexus.tatercomms.config.versions;

import dev.neuralnexus.tatercomms.config.TaterCommsConfig;
import dev.neuralnexus.tatercomms.config.sections.discord.DiscordConfig;
import dev.neuralnexus.tatercomms.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.tatercomms.config.sections.socket.SocketConfig;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import java.util.Set;

/** A class for TaterComms configuration. */
public class TaterCommsConfig_V1 implements TaterCommsConfig {
    private final int version;
    private final Set<ModuleConfig> modules;
    private final DiscordConfig discord;
    private final SocketConfig socket;
    private final FormattingConfig formatting;

    public TaterCommsConfig_V1(
            int version,
            Set<ModuleConfig> modules,
            DiscordConfig discord,
            FormattingConfig formatting,
            SocketConfig socket) {
        this.version = version;
        this.modules = modules;
        this.discord = discord;
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
    public SocketConfig socket() {
        return socket;
    }

    @Override
    public FormattingConfig formatting() {
        return formatting;
    }
}
