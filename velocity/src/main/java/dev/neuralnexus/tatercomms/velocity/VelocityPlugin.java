package dev.neuralnexus.tatercomms.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.velocity.logger.VelocityLogger;

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
    public VelocityPlugin(ProxyServer server, Logger logger) {
        pluginStart(server, new VelocityLogger(logger));
    }
}
