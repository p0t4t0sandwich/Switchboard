/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.platforms;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** NeoForge entry point. */
@Mod(Switchboard.PROJECT_ID)
public class NeoForgePlugin {
    public NeoForgePlugin() {
        Switchboard.instance()
                .pluginStart(
                        this,
                        ServerLifecycleHooks.getCurrentServer(),
                        LogUtils.getLogger(),
                        new LoggerAdapter(Switchboard.PROJECT_NAME, LogUtils.getLogger()));
    }
}
