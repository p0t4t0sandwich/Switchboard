package dev.neuralnexus.tatercomms.forge;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.common.logger.GenericLogger;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(TaterComms.Constants.PROJECT_ID)
public class ForgePlugin implements TaterCommsPlugin {
    public ForgePlugin() {
        pluginStart(
                this,
                new GenericLogger(
                        "[" + TaterComms.Constants.PROJECT_NAME + "] ",
                        TaterComms.Constants.PROJECT_ID));
    }
}
