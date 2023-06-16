package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public interface ServerStartedListener {
    default void taterServerStarted() {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();
                String server = TaterComms.getServerName();
                relay.sendSystemMessage(server, "**Server has started**");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
