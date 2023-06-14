package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeeTaterPlayer implements TaterPlayer {
    private ProxiedPlayer player;
    private String serverName;

    public BungeeTaterPlayer(ProxiedPlayer player) {
        this.player = player;
        if (player.getServer() != null) {
            this.serverName = player.getServer().getInfo().getName();
        } else {
            this.serverName = null;
        }
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
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
        player.sendMessage(new ComponentBuilder(message).create());
    }
}
