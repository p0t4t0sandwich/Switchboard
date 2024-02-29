package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.apache.logging.log4j.Logger;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/** Sponge entry point. */
@Plugin(Switchboard.Constants.PROJECT_ID)
public class Sponge8Plugin implements SwitchboardPlugin {
    @Inject
    public Sponge8Plugin(PluginContainer container, Logger logger) {
        pluginStart(
                container,
                null,
                logger,
                new LoggerAdapter(Switchboard.Constants.PROJECT_NAME, logger));
    }
}
