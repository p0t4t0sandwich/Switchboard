package dev.neuralnexus.tatercomms.platforms;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.minecraftforge.fml.common.Mod;

/** Forge entry point. */
@Mod(
        value = TaterComms.Constants.PROJECT_ID,
        modid = TaterComms.Constants.PROJECT_ID,
        useMetadata = true,
        serverSideOnly = true,
        acceptableRemoteVersions = "*")
public class ForgePlugin implements TaterCommsPlugin {
    public ForgePlugin() {
        pluginStart(this, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME));
    }
}
