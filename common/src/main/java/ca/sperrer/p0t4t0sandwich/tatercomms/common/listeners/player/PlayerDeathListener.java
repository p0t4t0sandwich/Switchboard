package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player deaths and sends them to the message relay.
 */
public interface PlayerDeathListener {
    /**
     * Called when a player dies, and sends it to the message relay.
     * @param taterPlayer The player.
     * @param deathMessage The death message.
     */
    default void taterPlayerDeath(TaterPlayer taterPlayer, String deathMessage) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();
                // Send death message through relay
                relay.sendSystemMessage(taterPlayer.getServerName(), deathMessage);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
