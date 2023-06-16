package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

public class PlayerAdvancementListener {
    public void taterPlayerAdvancement(TaterPlayer taterPlayer, String advancement) {
        MessageRelay relay = MessageRelay.getInstance();
        relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);
        relay.sendMessage(taterPlayer, taterPlayer.getServerName(), "has made the advancement [" + advancement + "]");
    }
}
