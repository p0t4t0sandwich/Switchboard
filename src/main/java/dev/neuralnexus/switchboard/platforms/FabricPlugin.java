package dev.neuralnexus.switchboard.platforms;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import net.fabricmc.api.ModInitializer;

/** Fabric entry point. */
public class FabricPlugin implements ModInitializer, SwitchboardPlugin {
    public FabricPlugin() {
        pluginStart(this, null, null, new LoggerAdapter(Switchboard.Constants.PROJECT_NAME));
    }

    @Override
    public void onInitialize() {}
}
