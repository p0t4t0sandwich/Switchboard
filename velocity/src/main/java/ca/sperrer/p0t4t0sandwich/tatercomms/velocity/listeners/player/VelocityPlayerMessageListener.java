package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player.VelocityTaterPlayer;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;

/**
 * Listens for player messages and sends it to the message relay.
 */
public class VelocityPlayerMessageListener implements PlayerMessageListener {
    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param event The player message event
     */
    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerMessage(PlayerChatEvent event) {
        // Get player and message
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!player.getCurrentServer().isPresent()) return;

        // Send message to message relay
        taterPlayerMessage(new VelocityTaterPlayer(player), message, true);
        event.setResult(PlayerChatEvent.ChatResult.denied());
    }
}
