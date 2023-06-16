package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;

public class BungeeServerStoppedListener implements ServerStoppedListener {
    public void onServerStopped() {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
