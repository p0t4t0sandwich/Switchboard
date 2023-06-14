package ca.sperrer.p0t4t0sandwich.tatercomms.forge.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ForgeTaterPlayer implements TaterPlayer {
    private final ServerPlayer player;
    private String serverName;

    public ForgeTaterPlayer(ServerPlayer player) {
        this.player = player;
        this.serverName = TaterComms.getServerName();
    }

    @Override
    public java.util.UUID getUUID() {
        return player.getUUID();
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
        player.displayClientMessage(Component.empty().append(message), false);
    }
}
