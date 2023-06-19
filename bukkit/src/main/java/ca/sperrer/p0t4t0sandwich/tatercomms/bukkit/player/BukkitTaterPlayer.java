package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Abstracts a Bukkit player to a TaterPlayer.
 */
public class BukkitTaterPlayer implements TaterPlayer {
    private final Player player;
    private String serverName;

    /**
     * Constructor.
     * @param player The Bukkit player.
     */
    public BukkitTaterPlayer(Player player) {
        this.player = player;
        this.serverName = TaterComms.getServerName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return player.getName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerName() {
        return serverName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setServerName(String server) {
        this.serverName = server;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
