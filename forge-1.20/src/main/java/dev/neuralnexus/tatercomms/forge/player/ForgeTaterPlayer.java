package dev.neuralnexus.tatercomms.forge.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

/**
 * Abstracts a Forge player to a TaterPlayer.
 */
public class ForgeTaterPlayer implements TaterPlayer {
    private final Player player;
    private String serverName;

    /**
     * Constructor.
     * @param player The Forge player.
     */
    public ForgeTaterPlayer(Player player) {
        this.player = player;
        this.serverName = TaterComms.getServerName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public java.util.UUID getUUID() {
        return player.getUUID();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return player.getName().getString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName() {
        return player.getDisplayName().getString();
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
        player.displayClientMessage(Component.empty().append(message), false);
    }
}
