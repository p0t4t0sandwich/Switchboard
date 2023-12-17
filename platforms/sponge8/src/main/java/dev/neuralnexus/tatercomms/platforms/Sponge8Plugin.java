package dev.neuralnexus.tatercomms.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.apache.logging.log4j.Logger;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/** Sponge entry point. */
@Plugin(TaterComms.Constants.PROJECT_ID)
public class Sponge8Plugin implements TaterCommsPlugin {
    @Inject
    public Sponge8Plugin(PluginContainer container, Logger logger) {
        pluginStart(container, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, logger));
    }
}
