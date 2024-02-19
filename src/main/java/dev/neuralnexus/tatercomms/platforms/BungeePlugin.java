package dev.neuralnexus.tatercomms.platforms;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

/** Bungee entry point. */
public class BungeePlugin extends Plugin implements TaterCommsPlugin {
    public BungeePlugin() {
        pluginStart(
                this,
                ProxyServer.getInstance(),
                getLogger(),
                new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, getLogger()));
    }
}
