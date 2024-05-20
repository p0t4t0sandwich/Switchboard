/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.slf4j.Logger;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

/** Sponge entry point. */
@Plugin(
        id = Switchboard.PROJECT_ID,
        name = Switchboard.PROJECT_NAME,
        version = Switchboard.PROJECT_VERSION,
        description = Switchboard.PROJECT_DESCRIPTION)
public class Sponge7Plugin {
    @Inject
    public Sponge7Plugin(PluginContainer container, Logger logger) {
        Switchboard.instance()
                .pluginStart(
                        container,
                        null,
                        logger,
                        new LoggerAdapter(Switchboard.PROJECT_NAME, logger));
    }
}
