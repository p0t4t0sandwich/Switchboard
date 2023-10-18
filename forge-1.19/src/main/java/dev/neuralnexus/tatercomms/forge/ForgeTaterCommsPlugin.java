package dev.neuralnexus.tatercomms.forge;

import com.mojang.logging.LogUtils;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.tatercomms.forge.commands.ForgeDiscordCommand;
import dev.neuralnexus.tatercomms.forge.networking.ModMessages;
import dev.neuralnexus.tatercomms.forge.networking.packet.ForgeCommsMessage;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
import dev.neuralnexus.taterlib.forge.TemplateForgePlugin;
import dev.neuralnexus.taterlib.forge.abstrations.logger.ForgeLogger;
import dev.neuralnexus.taterlib.forge.abstrations.player.ForgePlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * The TaterComms Forge plugin.
 */
@Mod(ForgeTaterCommsPlugin.MOD_ID)
public class ForgeTaterCommsPlugin extends TemplateForgePlugin implements TaterCommsPlugin {
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
    public ForgeTaterCommsPlugin() {
        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(ForgeDiscordCommand.class);
        pluginStart();

        // Register mod messages
        ModMessages.register();

        PlayerEvents.ADVANCEMENT_FINISHED.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String advancement = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterCommsConfig.serverName());
            CommsMessage commsMessage = new CommsMessage(commsSender, advancement);
            Player player = ((ForgePlayer) abstractPlayer).getPlayer();
            ModMessages.sendToPlayer(new ForgeCommsMessage(commsMessage), (ServerPlayer) player);
        });

        PlayerEvents.DEATH.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String deathMessage = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterCommsConfig.serverName());
            CommsMessage commsMessage = new CommsMessage(commsSender, deathMessage);
            Player player = ((ForgePlayer) abstractPlayer).getPlayer();
            ModMessages.sendToPlayer(new ForgeCommsMessage(commsMessage), (ServerPlayer) player);
        });
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
