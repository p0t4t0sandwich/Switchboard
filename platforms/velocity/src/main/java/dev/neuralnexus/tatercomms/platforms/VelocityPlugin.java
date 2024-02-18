package dev.neuralnexus.tatercomms.platforms;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.slf4j.Logger;

/** Velocity entry point. */
@Plugin(
        id = TaterComms.Constants.PROJECT_ID,
        name = TaterComms.Constants.PROJECT_NAME,
        version = TaterComms.Constants.PROJECT_VERSION,
        authors = TaterComms.Constants.PROJECT_AUTHORS,
        description = TaterComms.Constants.PROJECT_DESCRIPTION,
        url = TaterComms.Constants.PROJECT_URL,
        dependencies = {
            @Dependency(id = "taterlib"),
            @Dependency(id = "luckperms", optional = true)
        })
public class VelocityPlugin implements TaterCommsPlugin {
    @Inject
    public VelocityPlugin(PluginContainer plugin, ProxyServer server, Logger logger) {
        pluginStart(
                plugin,
                server,
                logger,
                new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, logger));
    }
}
