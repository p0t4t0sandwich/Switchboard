package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

public class PlayerLogoutListener {
    private final MessageRelay relay = MessageRelay.getInstance();

    public void taterPlayerLogout(TaterPlayer taterPlayer) {
        // Add the TaterPlayer to the cache
        relay.removeTaterPlayerFromCache(taterPlayer.getUUID());

        // Relay the logout message
        relay.sendPlayerLogout(taterPlayer, taterPlayer.getServerName());
    }
}
