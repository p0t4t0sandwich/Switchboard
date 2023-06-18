package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;

/**
 * Listens for server startup and sends it to the message relay.
 */
public class BukkitServerStartedListener implements ServerStartedListener {
    /**
     * Called when the server starts up.
     */
    public void onServerStarted() {
        // Send server started to message relay
        taterServerStarted();
    }
}
