package ca.sperrer.p0t4t0sandwich.tatercomms.common.relay;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;

public class MessageRelay {
    public MessageRelay() {
    }

    public void sendMessage(TaterPlayer player, String server, String message) {
        System.out.println(player.getDisplayName() + ": " + message);
    }
}
