package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface PlayerAdvancementListener {
    default void taterPlayerAdvancement(TaterPlayer taterPlayer, String advancement) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();
                relay.sendMessage(taterPlayer, taterPlayer.getServerName(), "has made the advancement [" + advancement + "]");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
