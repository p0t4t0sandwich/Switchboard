package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player advancements and sends them to the message relay.
 */
public interface PlayerAdvancementListener {
    /**
     * Called when a player makes an advancement, and sends it to the message relay.
     * @param taterPlayer The player.
     * @param advancement The advancement.
     */
    default void taterPlayerAdvancement(TaterPlayer taterPlayer, String advancement) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();
                relay.sendSystemMessage(taterPlayer.getServerName(), taterPlayer.getDisplayName() + " has made the advancement [" + advancement + "]");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
