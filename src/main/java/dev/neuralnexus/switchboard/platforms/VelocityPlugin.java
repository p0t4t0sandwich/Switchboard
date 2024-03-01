/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.slf4j.Logger;

/** Velocity entry point. */
@Plugin(
        id = Switchboard.Constants.PROJECT_ID,
        name = Switchboard.Constants.PROJECT_NAME,
        version = Switchboard.Constants.PROJECT_VERSION,
        authors = Switchboard.Constants.PROJECT_AUTHORS,
        description = Switchboard.Constants.PROJECT_DESCRIPTION,
        url = Switchboard.Constants.PROJECT_URL,
        dependencies = {@Dependency(id = "taterlib")})
public class VelocityPlugin implements SwitchboardPlugin {
    @Inject
    public VelocityPlugin(PluginContainer plugin, ProxyServer server, Logger logger) {
        pluginStart(
                plugin,
                server,
                logger,
                new LoggerAdapter(Switchboard.Constants.PROJECT_NAME, logger));
    }
}
