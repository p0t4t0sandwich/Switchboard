/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterloader.event.api.PluginEvents;

import org.spongepowered.api.plugin.Plugin;

/** Sponge entry point. */
@Plugin(
        id = Switchboard.PROJECT_ID,
        name = Switchboard.PROJECT_NAME,
        version = Switchboard.PROJECT_VERSION,
        description = Switchboard.PROJECT_DESCRIPTION)
public class Sponge7Plugin {
    @Inject
    public Sponge7Plugin() {
        PluginEvents.ENABLED.register(event -> Switchboard.instance().onEnable());
    }
}
