package dev.neuralnexus.tatercomms.platforms;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.fabricmc.api.ModInitializer;

/** Fabric entry point. */
public class FabricPlugin implements ModInitializer, TaterCommsPlugin {
    public FabricPlugin() {
        pluginStart(this, new LoggerAdapter(TaterComms.Constants.PROJECT_NAME));
    }

    @Override
    public void onInitialize() {}
}
