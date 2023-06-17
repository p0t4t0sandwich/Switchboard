package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;

/**
 * Listens for server shutdown and sends it to the message relay.
 */
public class BukkitServerStoppedListener implements ServerStoppedListener {
    /**
     * Called when the server stops.
     */
    public void onServerStopped() {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
