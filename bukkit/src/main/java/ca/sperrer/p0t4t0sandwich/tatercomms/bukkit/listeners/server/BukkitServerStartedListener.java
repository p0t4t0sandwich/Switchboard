package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class BukkitServerStartedListener implements Listener, ServerStartedListener {
    @EventHandler
    public void onServerStarted(ServerLoadEvent event) {
        if (event.getType() != ServerLoadEvent.LoadType.STARTUP) return;
        // Send server started to message relay
        taterServerStarted();
    }
}
