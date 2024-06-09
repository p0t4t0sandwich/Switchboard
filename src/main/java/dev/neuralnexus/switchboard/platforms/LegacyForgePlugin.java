/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import cpw.mods.fml.common.Mod;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

/** Legacy Forge entry point. */
@Mod(
        modid = Switchboard.PROJECT_ID,
        name = Switchboard.PROJECT_NAME,
        useMetadata = true,
        acceptableRemoteVersions = "*",
        bukkitPlugin = Switchboard.PROJECT_NAME)
public class LegacyForgePlugin {
    public LegacyForgePlugin() {
        Switchboard.instance()
                .pluginStart(this, null, null, new LoggerAdapter(Switchboard.PROJECT_ID));
    }
}
