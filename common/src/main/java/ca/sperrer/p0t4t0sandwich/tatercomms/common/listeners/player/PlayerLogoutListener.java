package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface PlayerLogoutListener {
    default void taterPlayerLogout(TaterPlayer taterPlayer) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Add the TaterPlayer to the cache
                relay.removeTaterPlayerFromCache(taterPlayer.getUUID());

                // Relay the logout message
                relay.sendPlayerLogout(taterPlayer, taterPlayer.getServerName());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
