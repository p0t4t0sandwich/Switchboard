package dev.neuralnexus.tatercomms.forge.networking.packet;

import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Do something
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            EntityType.COW.spawn(level, null, null, player.blockPosition(),
                    MobSpawnType.COMMAND, true, false);
        });
        return true;
    }
}
