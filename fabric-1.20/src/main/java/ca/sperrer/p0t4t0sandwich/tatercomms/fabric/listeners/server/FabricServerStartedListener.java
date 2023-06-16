package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class FabricServerStartedListener implements ServerLifecycleEvents.ServerStarted, ServerStartedListener {
    @Override
    public void onServerStarted(MinecraftServer server) {
        // Send server started to message relay
        taterServerStarted();
    }
}
