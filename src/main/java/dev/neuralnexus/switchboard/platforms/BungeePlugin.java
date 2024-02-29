package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

/** Bungee entry point. */
public class BungeePlugin extends Plugin implements SwitchboardPlugin {
    public BungeePlugin() {
        pluginStart(
                this,
                ProxyServer.getInstance(),
                getLogger(),
                new LoggerAdapter(Switchboard.Constants.PROJECT_NAME, getLogger()));
    }
}
