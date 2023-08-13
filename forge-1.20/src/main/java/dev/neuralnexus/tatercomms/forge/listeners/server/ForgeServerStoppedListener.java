package dev.neuralnexus.tatercomms.forge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for server stopped and sends it to the message relay.
 */
public class ForgeServerStoppedListener implements ServerStoppedListener {
    /**
     * Called when the server stops, and sends it to the message relay.
     * @param event The server stopped event
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
