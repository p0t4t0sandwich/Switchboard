package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bungee.logger.BungeeLogger;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Bungee entry point.
 */
public class BungeePlugin extends Plugin implements TaterCommsPlugin {
    public BungeePlugin() {
        ServerEvents.STOPPED.register(event -> pluginStop());
        pluginStart(this, new BungeeLogger(getLogger()));
    }
}
