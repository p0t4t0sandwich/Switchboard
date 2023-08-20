package dev.neuralnexus.tatercomms.fabric;

import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.fabric.commands.FabricDiscordCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.fabric.TemplateFabricPlugin;
import dev.neuralnexus.taterlib.fabric.abstractions.logger.FabricLogger;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;

/**
 * The TaterComms Fabric plugin.
 */
public class FabricTaterCommsPlugin extends TemplateFabricPlugin implements TaterCommsPlugin {
    private static final String MOD_ID = "tatercomms";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new FabricLogger("[TaterComms] ", LogManager.getLogger(MOD_ID));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register(FabricDiscordCommand::register);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onInitializeServer() {
        pluginStart();
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> pluginStop());
    }
}
