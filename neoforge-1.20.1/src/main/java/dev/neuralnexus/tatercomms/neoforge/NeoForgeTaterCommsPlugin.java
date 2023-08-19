package dev.neuralnexus.tatercomms.neoforge;

import com.mojang.logging.LogUtils;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.neoforge.commands.NeoForgeDiscordCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import dev.neuralnexus.taterlib.forge.abstrations.logger.ForgeLogger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NeoForgeTaterCommsPlugin extends TemplateForgePlugin implements TaterCommsPlugin {
    public static final String MOD_ID = "tatercomms";

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new ForgeLogger(LogUtils.getLogger());
    }

    /**
     * Called when the Forge mod is initializing.
     */
    public NeoForgeTaterCommsPlugin() {
        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(NeoForgeDiscordCommand.class);
        pluginStart();
    }

    /**
     * Called when the server is stopping.
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        pluginStop();
    }
}
