/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(
        value = Switchboard.Constants.PROJECT_ID,
        modid = Switchboard.Constants.PROJECT_ID,
        useMetadata = true,
        serverSideOnly = true,
        acceptableRemoteVersions = "*")
public class ForgePlugin implements SwitchboardPlugin {
    public ForgePlugin() {
        pluginStart(this, null, null, new LoggerAdapter(Switchboard.Constants.PROJECT_NAME));
    }
}
