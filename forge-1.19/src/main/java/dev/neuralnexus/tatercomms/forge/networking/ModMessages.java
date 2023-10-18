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
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(
                new ResourceLocation(ForgeTaterCommsPlugin.MOD_ID, "player_death"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ForgeCommsMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeCommsMessage::new)
                .encoder(ForgeCommsMessage::toBytes)
                .consumerMainThread(ForgeCommsMessage::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
