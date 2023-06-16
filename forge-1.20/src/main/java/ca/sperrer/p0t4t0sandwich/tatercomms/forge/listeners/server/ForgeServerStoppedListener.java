package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeServerStoppedListener implements ServerStoppedListener {
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
