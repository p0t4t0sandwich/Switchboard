package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;

public class BukkitServerStoppedListener implements ServerStoppedListener {
    public void onServerStopped() {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
