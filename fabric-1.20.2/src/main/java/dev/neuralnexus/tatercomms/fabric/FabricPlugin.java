package dev.neuralnexus.tatercomms.fabric;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;
import dev.neuralnexus.taterlib.common.logger.GenericLogger;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric entry point.
 */
public class FabricPlugin implements ModInitializer, TaterCommsPlugin {
    public FabricPlugin() {
        ServerEvents.STOPPED.register(event -> pluginStop());
        pluginStart(this, new GenericLogger("[" + TaterComms.Constants.PROJECT_NAME + "] ", TaterComms.Constants.PROJECT_ID));
    }

    @Override
    public void onInitialize() {}
}
