package dev.neuralnexus.tatercomms.forge;

import com.mojang.logging.LogUtils;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.tatercomms.forge.commands.ForgeDiscordCommand;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

import static dev.neuralnexus.tatercomms.forge.TaterCommsPacketHandler.PLAYER_ADVANCEMENT_FINISHED_INSTANCE;
import static dev.neuralnexus.tatercomms.forge.TaterCommsPacketHandler.PLAYER_DEATH_INSTANCE;

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
//        PLAYER_ADVANCEMENT_FINISHED_INSTANCE.registerMessage(0, ForgeCommsMessage.class, ForgeCommsMessage.encoder, ForgeCommsMessage.decoder, ForgeCommsMessage.messageConsumer);
//        PLAYER_DEATH_INSTANCE.registerMessage(1, ForgeCommsMessage.class, ForgeCommsMessage.encoder, ForgeCommsMessage.decoder, ForgeCommsMessage.messageConsumer);
//
//        PlayerEvents.ADVANCEMENT_FINISHED.register((args) -> {
//            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
//            String advancement = (String) args[1];
//            CommsSender commsSender = new CommsSender(abstractPlayer, TaterComms.getServerName());
//            CommsMessage commsMessage = new CommsMessage(commsSender, advancement);
//            Player player = ((ForgePlayer) abstractPlayer).getPlayer();
//            PLAYER_ADVANCEMENT_FINISHED_INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ForgeCommsMessage(commsMessage));
//        });
//
//        PlayerEvents.DEATH.register((args) -> {
//            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
//            String deathMessage = (String) args[1];
//            CommsSender commsSender = new CommsSender(abstractPlayer, TaterComms.getServerName());
//            CommsMessage commsMessage = new CommsMessage(commsSender, deathMessage);
//            Player player = ((ForgePlayer) abstractPlayer).getPlayer();
//            PLAYER_DEATH_INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ForgeCommsMessage(commsMessage));
//        });


        // Register server starting/stopping events
        MinecraftForge.EVENT_BUS.register(this);

        // Register commands
        MinecraftForge.EVENT_BUS.register(ForgeDiscordCommand.class);
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
