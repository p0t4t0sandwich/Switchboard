package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.ForgeMain;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for server starting and starts TaterComms.
 */
public class ForgeServerStartingListener {
    ForgeMain plugin = ForgeMain.getInstance();

    /**
     * Called when the server starts, and starts TaterComms.
     * @param event The server starting event
     */
    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        try {
            // Start TaterComms
            plugin.taterComms = new TaterComms("config", plugin.logger);
            plugin.taterComms.start();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
