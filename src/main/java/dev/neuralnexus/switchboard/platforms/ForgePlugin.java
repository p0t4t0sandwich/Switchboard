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
