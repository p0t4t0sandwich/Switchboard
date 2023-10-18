package dev.neuralnexus.tatercomms.forge.networking;

import dev.neuralnexus.tatercomms.forge.ForgeTaterCommsPlugin;
import dev.neuralnexus.tatercomms.forge.networking.packet.ForgeCommsMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel PLAYER_ADVANCEMENT_FINISHED_INSTANCE;
    private static SimpleChannel PLAYER_DEATH_INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        PLAYER_ADVANCEMENT_FINISHED_INSTANCE = NetworkRegistry.ChannelBuilder.named(
                new ResourceLocation(ForgeTaterCommsPlugin.MOD_ID, "player_advancement_finished"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        PLAYER_ADVANCEMENT_FINISHED_INSTANCE.messageBuilder(ForgeCommsMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeCommsMessage::new)
                .encoder(ForgeCommsMessage::toBytes)
                .consumerMainThread(ForgeCommsMessage::handle)
                .add();


        PLAYER_DEATH_INSTANCE = NetworkRegistry.ChannelBuilder.named(
                new ResourceLocation(ForgeTaterCommsPlugin.MOD_ID, "player_death"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        PLAYER_DEATH_INSTANCE.messageBuilder(ForgeCommsMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeCommsMessage::new)
                .encoder(ForgeCommsMessage::toBytes)
                .consumerMainThread(ForgeCommsMessage::handle)
                .add();
    }

    public static <MSG> void sendPlayerAdvancementFinishedToProxy(MSG message, ServerPlayer player) {
        PLAYER_ADVANCEMENT_FINISHED_INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendPlayerDeathToProxy(MSG message, ServerPlayer player) {
        PLAYER_DEATH_INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
