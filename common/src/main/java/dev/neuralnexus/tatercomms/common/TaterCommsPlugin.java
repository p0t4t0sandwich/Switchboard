package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.taterlib.common.Plugin;
import dev.neuralnexus.taterlib.common.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.common.logger.AbstractLogger;

/**
 * The main plugin interface.
 */
public interface TaterCommsPlugin extends Plugin {
    /**
     * Starts the plugin.
     */
    default void pluginStart(Object plugin, AbstractLogger logger) {
        logger.info(TaterComms.Constants.PROJECT_NAME + " is running on " + TaterAPIProvider.get().serverType() + " " + TaterAPIProvider.get().minecraftVersion() + "!");
        TaterComms.start(plugin, logger);
    }

    /**
     * Stops the plugin.
     */
    default void pluginStop() {
        TaterComms.stop();
    }
}
