package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitTaterPlayer implements TaterPlayer {
    private Player player;

    public BukkitTaterPlayer(Player player) {
        this.player = player;
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
    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
