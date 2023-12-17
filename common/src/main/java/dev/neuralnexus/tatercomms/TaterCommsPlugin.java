package dev.neuralnexus.tatercomms;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.PluginEvents;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.Plugin;

/** The main plugin interface. */
public interface TaterCommsPlugin extends Plugin {
    /** Starts the plugin. */
    default void pluginStart(Object plugin, AbstractLogger logger) {
        logger.info(
                TaterComms.Constants.PROJECT_NAME
                        + " is running on "
                        + TaterAPIProvider.serverType()
                        + " "
                        + TaterAPIProvider.minecraftVersion()
                        + "!");
        PluginEvents.DISABLED.register(event -> pluginStop());
        TaterComms.start(plugin, logger);
    }

    /** Stops the plugin. */
    default void pluginStop() {
        TaterComms.stop();
    }
}
