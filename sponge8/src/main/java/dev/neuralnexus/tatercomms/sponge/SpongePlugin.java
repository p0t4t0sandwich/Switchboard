package dev.neuralnexus.tatercomms.sponge;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;
import dev.neuralnexus.taterlib.sponge.logger.SpongeLogger;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

/**
 * Sponge entry point.
 */
@Plugin(TaterComms.Constants.PROJECT_ID)
public class SpongePlugin implements TaterCommsPlugin {
    @Inject
    public SpongePlugin(PluginContainer container, Logger logger) {
        ServerEvents.STOPPED.register(event -> pluginStop());
        pluginStart(container, new SpongeLogger(logger));
    }
}
