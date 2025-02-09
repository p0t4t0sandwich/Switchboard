/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

import net.neoforged.fml.common.Mod;

/** NeoForge entry point. */
@Mod(SwitchboardPlugin.PROJECT_ID)
public class NeoForgePlugin {
    public NeoForgePlugin() {
        PluginEvents.ENABLED.register(event -> SwitchboardPlugin.instance().onEnable());
    }
}
