package dev.neuralnexus.tatercomms.platforms;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.neoforged.fml.common.Mod;

/** NeoForge entry point. */
@Mod(TaterComms.Constants.PROJECT_ID)
public class NeoForgePlugin implements TaterCommsPlugin {
    public NeoForgePlugin() {
        pluginStart(
                this, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, LogUtils.getLogger()));
    }
}
