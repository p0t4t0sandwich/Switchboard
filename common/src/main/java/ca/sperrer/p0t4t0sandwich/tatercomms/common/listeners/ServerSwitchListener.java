package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import java.util.UUID;

public class ServerSwitchListener {
    public void taterServerSwitch(TaterPlayer taterPlayer, String toServer) {
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
    }
}
