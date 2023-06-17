package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;

/**
 * Listens for server stopped and sends it to the message relay.
 */
public class VelocityServerStoppedListener implements ServerStoppedListener {
    /**
     * Called when the server stops, and sends it to the message relay.
     * @param event The server stopped event
     */
    @Subscribe
    public void onServerStopped(ProxyShutdownEvent event) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
