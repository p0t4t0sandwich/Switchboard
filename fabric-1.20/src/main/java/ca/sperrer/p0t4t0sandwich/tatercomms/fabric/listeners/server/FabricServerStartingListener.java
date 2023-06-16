package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class FabricServerStartingListener implements ServerLifecycleEvents.ServerStarting {
    FabricMain plugin = FabricMain.getInstance();

    @Override
    public void onServerStarting(MinecraftServer server) {
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
