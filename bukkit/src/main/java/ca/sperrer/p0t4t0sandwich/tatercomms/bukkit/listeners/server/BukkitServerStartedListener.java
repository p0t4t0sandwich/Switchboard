package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

/**
 * Listens for server startup and sends it to the message relay.
 */
public class BukkitServerStartedListener implements Listener, ServerStartedListener {
    /**
     * Called when the server starts up.
     * @param event The event.
     */
    @EventHandler
    public void onServerStarted(ServerLoadEvent event) {
        if (event.getType() != ServerLoadEvent.LoadType.STARTUP) return;
        // Send server started to message relay
        taterServerStarted();
    }
}
