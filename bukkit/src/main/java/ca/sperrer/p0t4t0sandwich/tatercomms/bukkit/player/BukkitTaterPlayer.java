package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.BukkitMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitTaterPlayer implements TaterPlayer {
    BukkitMain plugin = BukkitMain.getInstance();
    private Player player;
    private String serverName;

    public BukkitTaterPlayer(Player player) {
        this.player = player;
        this.serverName = plugin.taterComms.getServerName();
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
        player.sendMessage(message);
    }
}
