package dev.neuralnexus.tatercomms.forge;

import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgeCommsMessage extends CommsMessage {
    public ForgeCommsMessage(CommsMessage message) {
        super(message.getSender(), message.getMessage());
    }

    public static BiConsumer<ForgeCommsMessage, FriendlyByteBuf> encoder = (msg, buf) -> buf.writeByteArray(msg.toByteArray());
    public static Function<FriendlyByteBuf, ForgeCommsMessage> decoder = (buf) -> (ForgeCommsMessage) ForgeCommsMessage.fromByteArray(buf.readByteArray());

    // This doesn't actually do anything, just needed to register the message
    public static BiConsumer<ForgeCommsMessage, Supplier<NetworkEvent.Context>> messageConsumer = (message, contextSupplier) -> contextSupplier.get().setPacketHandled(true);
}
