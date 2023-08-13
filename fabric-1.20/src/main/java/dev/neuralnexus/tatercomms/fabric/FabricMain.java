package dev.neuralnexus.tatercomms.fabric;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.fabric.commands.FabricDiscordCommand;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class for the Fabric plugin.
 */
public class FabricMain implements DedicatedServerModInitializer {
    public TaterComms taterComms;

    // Logger
    public final Logger logger = LoggerFactory.getLogger("tatercomms");

    /**
     * Gets the server type.
     * @return The server type
     */
    public String getServerType() {
        return "Fabric";
    }

    // Singleton instance
    private static FabricMain instance;

    /**
     * Gets the singleton instance.
     * @return The singleton instance
     */
    public static FabricMain getInstance() {
        return instance;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onInitializeServer() {
        // Singleton instance
        instance = this;

        // Register commands
        CommandRegistrationCallback.EVENT.register(FabricDiscordCommand::register);

        logger.info("[TaterComms]: TaterComms is running on " + getServerType() + ".");

        // Mod enable message
        logger.info("[TaterComms]: TaterComms has been enabled!");
    }
}
