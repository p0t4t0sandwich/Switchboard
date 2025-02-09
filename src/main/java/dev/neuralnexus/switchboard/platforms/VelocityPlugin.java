/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;

import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

/** Velocity entry point. */
@Plugin(
        id = SwitchboardPlugin.PROJECT_ID,
        name = SwitchboardPlugin.PROJECT_NAME,
        version = SwitchboardPlugin.PROJECT_VERSION,
        authors = SwitchboardPlugin.PROJECT_AUTHORS,
        description = SwitchboardPlugin.PROJECT_DESCRIPTION,
        url = SwitchboardPlugin.PROJECT_URL,
        dependencies = {@Dependency(id = "taterlib")})
public class VelocityPlugin {
    @Inject
    public VelocityPlugin() {
        PluginEvents.ENABLED.register(event -> SwitchboardPlugin.instance().onEnable());
    }
}
