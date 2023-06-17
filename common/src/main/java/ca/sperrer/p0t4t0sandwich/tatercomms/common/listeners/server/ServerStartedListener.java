package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for server starts and sends them to the message relay.
 */
public interface ServerStartedListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     */
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
