package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;

public class VelocityServerStartedListener implements ServerStartedListener {
    public void onServerStarted() {
        // Send server started to message relay
        taterServerStarted();
    }
}
