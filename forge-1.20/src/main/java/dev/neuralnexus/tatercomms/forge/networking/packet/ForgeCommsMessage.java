package dev.neuralnexus.tatercomms.forge.networking.packet;

import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForgeCommsMessage extends CommsMessage {
    public ForgeCommsMessage(CommsMessage message) {
        super(message.getSender(), message.getMessage());
    }

    public ForgeCommsMessage(FriendlyByteBuf friendlyByteBuf) {
        super(
                (CommsMessage.fromByteArray(friendlyByteBuf.readByteArray())).getSender(),
                (CommsMessage.fromByteArray(friendlyByteBuf.readByteArray())).getMessage()
        );
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByteArray(this.toByteArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {}
}
