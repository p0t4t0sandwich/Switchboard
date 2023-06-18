package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player logins and sends them to the message relay.
 */
public interface PlayerLoginListener {
    /**
     * Called when a player logs in, and sends it to the message relay.
     * @param taterPlayer The player.
     */
    default void taterPlayerLogin(TaterPlayer taterPlayer) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Add the TaterPlayer to the cache
                relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

                // Relay the login message
                relay.sendSystemMessage(taterPlayer.getServerName(), taterPlayer.getDisplayName() + " joined the game");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
