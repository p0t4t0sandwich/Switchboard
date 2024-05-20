/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

/** Bungee entry point. */
public class BungeePlugin extends Plugin {
    public BungeePlugin() {
        Switchboard.instance()
                .pluginStart(
                        this,
                        ProxyServer.getInstance(),
                        getLogger(),
                        new LoggerAdapter(Switchboard.PROJECT_NAME, getLogger()));
    }
}
