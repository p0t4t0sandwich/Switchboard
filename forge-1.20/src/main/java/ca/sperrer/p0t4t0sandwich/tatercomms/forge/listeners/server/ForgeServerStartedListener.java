package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeServerStartedListener implements ServerStartedListener {
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        // Send server started to message relay
        taterServerStarted();
    }
}
