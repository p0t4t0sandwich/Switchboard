package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;

public class VelocityServerStoppedListener implements ServerStoppedListener {
    @Subscribe
    public void onServerStopped(ProxyShutdownEvent event) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
