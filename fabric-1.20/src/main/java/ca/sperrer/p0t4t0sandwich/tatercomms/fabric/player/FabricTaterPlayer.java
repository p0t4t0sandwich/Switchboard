package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FabricTaterPlayer implements TaterPlayer {
    private ServerPlayerEntity player;

    public FabricTaterPlayer(ServerPlayerEntity player) {
        this.player = player;
    }

    @Override
    public java.util.UUID getUUID() {
        return player.getUuid();
    }

    @Override
    public String getName() {
        return player.getName().toString();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName().toString();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(Text.literal(message));
    }
}
