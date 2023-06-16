package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;

public class BungeeServerStartedListener implements ServerStartedListener {
    public void onServerStarted() {
        // Send server started to message relay
        taterServerStarted();
    }
}
