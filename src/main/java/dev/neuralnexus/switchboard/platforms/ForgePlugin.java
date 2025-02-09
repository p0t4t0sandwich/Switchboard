/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterapi.event.api.PluginEvents;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(
        value = Switchboard.PROJECT_ID,
        modid = Switchboard.PROJECT_ID,
        useMetadata = true,
        serverSideOnly = true,
        acceptableRemoteVersions = "*")
public class ForgePlugin {
    public ForgePlugin() {
        PluginEvents.ENABLED.register(event -> Switchboard.instance().onEnable());
    }
}
