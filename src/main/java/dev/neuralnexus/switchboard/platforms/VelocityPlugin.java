/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

/** Velocity entry point. */
@Plugin(
        id = Switchboard.PROJECT_ID,
        name = Switchboard.PROJECT_NAME,
        version = Switchboard.PROJECT_VERSION,
        authors = Switchboard.PROJECT_AUTHORS,
        description = Switchboard.PROJECT_DESCRIPTION,
        url = Switchboard.PROJECT_URL,
        dependencies = {@Dependency(id = "taterlib")})
public class VelocityPlugin {
    @Inject
    public VelocityPlugin() {
        PluginEvents.ENABLED.register(event -> Switchboard.instance().onEnable());
    }
}
