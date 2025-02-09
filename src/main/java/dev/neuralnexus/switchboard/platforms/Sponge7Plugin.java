/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

import org.spongepowered.api.plugin.Plugin;

/** Sponge entry point. */
@Plugin(
        id = SwitchboardPlugin.PROJECT_ID,
        name = SwitchboardPlugin.PROJECT_NAME,
        version = SwitchboardPlugin.PROJECT_VERSION,
        description = SwitchboardPlugin.PROJECT_DESCRIPTION)
public class Sponge7Plugin {
    @Inject
    public Sponge7Plugin() {
        PluginEvents.ENABLED.register(event -> SwitchboardPlugin.instance().onEnable());
    }
}
