package ca.sperrer.p0t4t0sandwich.tatercomms.forge;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player.ForgePlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player.ForgePlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player.ForgePlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player.ForgePlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server.ForgeServerStartedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server.ForgeServerStartingListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server.ForgeServerStoppedListener;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Main class for the Forge plugin.
 */
@Mod(ForgeMain.MODID)
public class ForgeMain {
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
    private static ForgeMain instance;

    /**
     * Gets the singleton instance.
     * @return The singleton instance
     */
    public static ForgeMain getInstance() {
        return instance;
    }

    /**
     * Constructor.
     */
    public ForgeMain() {
        // Singleton instance
        instance = this;

        logger.info("[TaterComms]: TaterComms is running on " + getServerType() + ".");

        // Start TaterComms
        MinecraftForge.EVENT_BUS.register(new ForgeServerStartingListener());

        // Register player event listeners
        MinecraftForge.EVENT_BUS.register(new ForgePlayerAdvancementListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerLoginListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerLogoutListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerMessageListener());

        // Register server event listeners
        MinecraftForge.EVENT_BUS.register(new ForgeServerStartedListener());
        MinecraftForge.EVENT_BUS.register(new ForgeServerStoppedListener());

        // Register commands
//        MinecraftForge.EVENT_BUS.register(ForgeTemplateCommand.class);

        // Mod enable message
        logger.info("[TaterComms]: TaterComms has been enabled!");
    }
}
