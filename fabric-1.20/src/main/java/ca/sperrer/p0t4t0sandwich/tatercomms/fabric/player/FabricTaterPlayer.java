package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FabricTaterPlayer implements TaterPlayer {
    private final ServerPlayerEntity player;
    private String serverName;

    public FabricTaterPlayer(ServerPlayerEntity player) {
        this.player = player;
        this.serverName = TaterComms.getServerName();
    }

    @Override
    public java.util.UUID getUUID() {
        return player.getUuid();
    }

    @Override
    public String getName() {
        return player.getName().getString();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName().getString();
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public void setServerName(String server) {
        this.serverName = server;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(Text.literal(message));
    }
}
