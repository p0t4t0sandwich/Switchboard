package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player messages and sends them to the message relay.
 */
public interface PlayerMessageListener {
    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param taterPlayer The player.
     * @param message The message.
     */
    default void taterPlayerMessage(TaterPlayer taterPlayer, String message) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Send message through relay
                relay.sendMessage(taterPlayer, taterPlayer.getServerName(), message);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
