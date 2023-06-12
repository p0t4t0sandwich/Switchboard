package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class VelocityTaterPlayer implements TaterPlayer {
    private Player player;
    private String serverName;

    public VelocityTaterPlayer(Player player) {
        this.player = player;
        if (!player.getCurrentServer().isPresent()) {
            this.serverName = "null";
        }
        this.serverName = player.getCurrentServer().get().getServerInfo().getName();
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public String getDisplayName() {
        return player.getUsername();
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
        player.sendMessage(Component.text(message));
    }
}
