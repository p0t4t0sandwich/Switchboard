package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bungee.logger.BungeeLogger;

import net.md_5.bungee.api.plugin.Plugin;

/** Bungee entry point. */
public class BungeePlugin extends Plugin implements TaterCommsPlugin {
    public BungeePlugin() {
        pluginStart(this, new BungeeLogger(getLogger()));
    }
}
