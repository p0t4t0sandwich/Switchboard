package dev.neuralnexus.tatercomms.platforms;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(TaterComms.Constants.PROJECT_ID)
public class ForgePlugin implements TaterCommsPlugin {
    public ForgePlugin() {
        pluginStart(this, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME));
    }
}
