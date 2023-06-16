package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface PlayerServerSwitchListener {
    default void taterServerSwitch(TaterPlayer taterPlayer, String toServer) {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();

                // Get TaterPlayer from cache
                TaterPlayer cachedTaterPlayer = relay.getTaterPlayerFromCache(taterPlayer.getUUID());
                if (cachedTaterPlayer == null) {
                    return;
                }

                // Get fromServer
                String fromServer = taterPlayer.getServerName();

                // Update the server name and TaterPlayer object
                taterPlayer.setServerName(toServer);
                relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

                // Relay the server switch message
                relay.sendPlayerServerSwitch(taterPlayer, fromServer, toServer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
