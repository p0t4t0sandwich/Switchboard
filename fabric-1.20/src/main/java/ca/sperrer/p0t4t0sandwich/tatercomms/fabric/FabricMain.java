package ca.sperrer.p0t4t0sandwich.tatercomms.fabric;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.commands.FabricDiscordCommand;
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

        logger.info("[TaterComms]: TaterComms is running on " + getServerType() + ".");

        // Register commands
        CommandRegistrationCallback.EVENT.register(FabricDiscordCommand::register);

        // Mod enable message
        logger.info("[TaterComms]: TaterComms has been enabled!");
    }
}
