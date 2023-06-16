package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class FabricServerStoppedListener implements ServerLifecycleEvents.ServerStopped, ServerStoppedListener {
    @Override
    public void onServerStopped(MinecraftServer server) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
