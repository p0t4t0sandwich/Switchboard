package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

/**
 * Listens for server start and sends it to the message relay.
 */
public class FabricServerStartedListener implements ServerLifecycleEvents.ServerStarted, ServerStartedListener {
    /**
     * Called when the server starts, and sends it to the message relay.
     * @param server The server
     */
    @Override
    public void onServerStarted(MinecraftServer server) {
        // Send server started to message relay
        taterServerStarted();
    }
}
