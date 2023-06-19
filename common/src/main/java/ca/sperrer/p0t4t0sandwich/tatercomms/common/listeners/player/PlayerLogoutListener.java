package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player logouts and sends them to the message relay.
 */
public interface PlayerLogoutListener {
    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param taterPlayer The player.
     */
    default void taterPlayerLogout(TaterPlayer taterPlayer) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Remove the TaterPlayer from the cache
                relay.removeTaterPlayerFromCache(taterPlayer.getUUID());

                // Relay the logout message
                relay.sendSystemMessage(taterPlayer.getServerName(), taterPlayer.getDisplayName() + " left the game");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
