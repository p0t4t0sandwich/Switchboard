package ca.sperrer.p0t4t0sandwich.tatercomms.forge;

import ca.sperrer.p0t4t0sandwich.tatercomms.forge.commands.ForgeTemplateCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.*;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ForgeMain.MODID)
public class ForgeMain {
    public TaterComms taterComms;
    public static final String MODID = "tatercomms";
    public static final Logger logger = LogUtils.getLogger();

    // Get server type
    public String getServerType() {
        return "Forge";
    }

    // Singleton instance
    private static ForgeMain instance;

    public static ForgeMain getInstance() {
        return instance;
    }

    public ForgeMain() {
        // Singleton instance
        instance = this;

        logger.info("[TaterComms]: TaterComms is running on " + getServerType() + ".");

        // Register event listeners
        MinecraftForge.EVENT_BUS.register(new ForgePlayerAdvancementListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerLoginListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerLogoutListener());
        MinecraftForge.EVENT_BUS.register(new ForgePlayerMessageListener());
        MinecraftForge.EVENT_BUS.register(new ForgeServerStartedListener());
        MinecraftForge.EVENT_BUS.register(new ForgeServerStartingListener());

        // Register commands
        MinecraftForge.EVENT_BUS.register(ForgeTemplateCommand.class);

        // Mod enable message
        logger.info("[TaterComms]: TaterComms has been enabled!");
    }
}
