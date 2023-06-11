package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class VelocityTaterPlayer implements TaterPlayer {
    private Player player;

    public VelocityTaterPlayer(Player player) {
        this.player = player;
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
    public void sendMessage(String message) {
        player.sendMessage(Component.text(message));
    }
}
