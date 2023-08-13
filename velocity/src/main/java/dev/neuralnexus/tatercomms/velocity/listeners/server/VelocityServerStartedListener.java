package dev.neuralnexus.tatercomms.velocity.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;

/**
 * Listens for server started and sends it to the message relay.
 */
public class VelocityServerStartedListener implements ServerStartedListener {
    /**
     * Called when the server starts.
     */
    public void onServerStarted() {
        // Send server started to message relay
        taterServerStarted();
    }
}
