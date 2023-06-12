package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

public class PlayerMessageListener {
    private final MessageRelay relay = MessageRelay.getInstance();

    public void taterPlayerMessage(TaterPlayer taterPlayer, String message) {
        // Send message through relay
        relay.sendMessage(taterPlayer, taterPlayer.getServerName(), message);
    }
}
