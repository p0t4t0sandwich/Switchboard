/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterloader.event.api.PluginEvents;

import org.spongepowered.plugin.builtin.jvm.Plugin;

/** Sponge entry point. */
@Plugin(Switchboard.PROJECT_ID)
public class Sponge8Plugin {
    @Inject
    public Sponge8Plugin() {
        PluginEvents.ENABLED.register(event -> Switchboard.instance().onEnable());
    }
}
