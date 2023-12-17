package com.example.pluginname.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.slf4j.Logger;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

/** Sponge entry point. */
@Plugin(
        id = TaterComms.Constants.PROJECT_ID,
        name = TaterComms.Constants.PROJECT_NAME,
        version = TaterComms.Constants.PROJECT_VERSION,
        description = TaterComms.Constants.PROJECT_DESCRIPTION)
public class Sponge7Plugin implements TaterCommsPlugin {
    @Inject
    public Sponge7Plugin(PluginContainer container, Logger logger) {
        pluginStart(container, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, logger));
    }
}
