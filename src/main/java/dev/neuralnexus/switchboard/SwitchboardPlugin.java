/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.PluginEvents;
import dev.neuralnexus.taterlib.logger.AbstractLogger;
import dev.neuralnexus.taterlib.plugin.Plugin;

/** The main plugin interface. */
public interface SwitchboardPlugin extends Plugin {
    @Override
    default String name() {
        return Switchboard.Constants.PROJECT_NAME;
    }

    @Override
    default String id() {
        return Switchboard.Constants.PROJECT_ID;
    }

    @Override
    default void pluginStart(
            Object plugin, Object pluginServer, Object pluginLogger, AbstractLogger logger) {
        logger.info(
                Switchboard.Constants.PROJECT_NAME
                        + " is running on "
                        + TaterAPIProvider.serverType()
                        + " "
                        + TaterAPIProvider.minecraftVersion()
                        + "!");
        PluginEvents.DISABLED.register(event -> pluginStop());
        Switchboard.start(plugin, pluginServer, pluginLogger, logger);
    }

    @Override
    default void pluginStop() {
        Switchboard.stop();
    }
}
