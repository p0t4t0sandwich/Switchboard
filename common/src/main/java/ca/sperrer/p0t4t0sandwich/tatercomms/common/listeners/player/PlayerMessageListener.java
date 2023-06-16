package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface PlayerMessageListener {
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
