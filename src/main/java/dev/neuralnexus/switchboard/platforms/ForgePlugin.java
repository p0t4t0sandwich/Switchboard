/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

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
        Switchboard.instance()
                .pluginStart(this, null, null, new LoggerAdapter(Switchboard.PROJECT_NAME));
    }
}
