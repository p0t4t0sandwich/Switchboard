package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface PlayerLoginListener {
    default void taterPlayerLogin(TaterPlayer taterPlayer) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Add the TaterPlayer to the cache
                relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

                // Relay the login message
                relay.sendPlayerLogin(taterPlayer, taterPlayer.getServerName());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
