package ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for server starts and sends them to the message relay.
 */
public interface ServerStoppedListener {
    /**
     * Called when a server stops, and sends it to the message relay.
     */
    default void taterServerStopped() {
        runTaskAsync(() -> {
            try {
                MessageRelay relay = MessageRelay.getInstance();
                String server = TaterComms.getServerName();
                relay.sendSystemMessage(server, "**Server has stopped**");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
