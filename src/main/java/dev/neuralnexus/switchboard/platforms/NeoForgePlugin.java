package dev.neuralnexus.switchboard.platforms;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** NeoForge entry point. */
@Mod(Switchboard.Constants.PROJECT_ID)
public class NeoForgePlugin implements SwitchboardPlugin {
    public NeoForgePlugin() {
        pluginStart(
                this,
                ServerLifecycleHooks.getCurrentServer(),
                LogUtils.getLogger(),
                new LoggerAdapter(Switchboard.Constants.PROJECT_NAME, LogUtils.getLogger()));
    }
}
