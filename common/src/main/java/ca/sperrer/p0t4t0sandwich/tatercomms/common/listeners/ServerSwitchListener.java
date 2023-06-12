package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import java.util.UUID;

public class ServerSwitchListener {
    private final MessageRelay relay = MessageRelay.getInstance();

    public void taterServerSwitch(UUID playerUUID, String toServer) {
        // Get TaterPlayer from cache
        TaterPlayer taterPlayer = relay.getTaterPlayerFromCache(playerUUID);

        // Get fromServer
        String fromServer = taterPlayer.getServerName();

        // Update the server name and TaterPlayer object
        taterPlayer.setServerName(toServer);
        relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

        // Relay the server switch message
        relay.sendPlayerServerSwitch(taterPlayer, fromServer, toServer);
    }
}
