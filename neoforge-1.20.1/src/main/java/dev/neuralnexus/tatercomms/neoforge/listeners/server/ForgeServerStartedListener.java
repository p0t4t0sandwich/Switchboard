package dev.neuralnexus.tatercomms.neoforge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for server started and sends it to the message relay.
 */
public class ForgeServerStartedListener implements ServerStartedListener {
    /**
     * Called when the server starts.
     * @param event The server started event
     */
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        // Send server started to message relay
        taterServerStarted();
    }
}
