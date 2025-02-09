/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import cpw.mods.fml.common.Mod;

import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

/** Legacy Forge entry point. */
@Mod(
        modid = SwitchboardPlugin.PROJECT_ID,
        name = SwitchboardPlugin.PROJECT_NAME,
        useMetadata = true,
        acceptableRemoteVersions = "*",
        bukkitPlugin = SwitchboardPlugin.PROJECT_NAME)
public class LegacyForgePlugin {
    public LegacyForgePlugin() {
        PluginEvents.ENABLED.register(event -> SwitchboardPlugin.instance().onEnable());
    }
}
