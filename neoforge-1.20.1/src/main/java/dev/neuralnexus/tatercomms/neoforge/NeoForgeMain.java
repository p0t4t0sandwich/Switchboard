package dev.neuralnexus.tatercomms.neoforge;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.neoforge.commands.NeoForgeDiscordCommand;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Main class for the Forge plugin.
 */
@Mod(NeoForgeMain.MODID)
public class NeoForgeMain {
    public TaterComms taterComms;
    public static final String MODID = "tatercomms";
    public static final Logger logger = LogUtils.getLogger();

    /**
     * Gets the server type.
     * @return The server type
     */
    public String getServerType() {
        return "Forge";
    }

    // Singleton instance
    private static NeoForgeMain instance;

    /**
     * Gets the singleton instance.
     * @return The singleton instance
     */
    public static NeoForgeMain getInstance() {
        return instance;
    }

    /**
     * Constructor.
     */
    public NeoForgeMain() {
        // Singleton instance
        instance = this;

        logger.info("[TaterComms]: TaterComms is running on " + getServerType() + ".");

        // Register commands
        MinecraftForge.EVENT_BUS.register(NeoForgeDiscordCommand.class);

        // Mod enable message
        logger.info("[TaterComms]: TaterComms has been enabled!");
    }
}
