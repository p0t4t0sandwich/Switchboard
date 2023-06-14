package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

public class PlayerMessageListener {
    public void taterPlayerMessage(TaterPlayer taterPlayer, String message) {
        MessageRelay relay = MessageRelay.getInstance();

        // Send message through relay
        relay.sendMessage(taterPlayer, taterPlayer.getServerName(), message);
    }
}
