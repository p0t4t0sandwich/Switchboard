package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

public class PlayerLoginListener {
    public void taterPlayerLogin(TaterPlayer taterPlayer) {
        MessageRelay relay = MessageRelay.getInstance();

        // Add the TaterPlayer to the cache
        relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

        // Relay the login message
        relay.sendPlayerLogin(taterPlayer, taterPlayer.getServerName());
    }
}
