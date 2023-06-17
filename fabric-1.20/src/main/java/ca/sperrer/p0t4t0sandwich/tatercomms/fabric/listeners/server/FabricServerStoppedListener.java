package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

/**
 * Listens for server stop and sends it to the message relay.
 */
public class FabricServerStoppedListener implements ServerLifecycleEvents.ServerStopped, ServerStoppedListener {
    /**
     * Called when the server stops, and sends it to the message relay.
     * @param server The server
     */
    @Override
    public void onServerStopped(MinecraftServer server) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
