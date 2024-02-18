package dev.neuralnexus.tatercomms.platforms;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** NeoForge entry point. */
@Mod(TaterComms.Constants.PROJECT_ID)
public class NeoForgePlugin implements TaterCommsPlugin {
    public NeoForgePlugin() {
        pluginStart(
                this,
                ServerLifecycleHooks.getCurrentServer(),
                LogUtils.getLogger(),
                new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, LogUtils.getLogger()));
    }
}
