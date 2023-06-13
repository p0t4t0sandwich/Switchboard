package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.ForgeMain;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeServerStartedListener {
    ForgeMain plugin = ForgeMain.getInstance();

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        try {
            // Start Template
            plugin.taterComms = new TaterComms("config", plugin.logger);
            plugin.taterComms.start();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
