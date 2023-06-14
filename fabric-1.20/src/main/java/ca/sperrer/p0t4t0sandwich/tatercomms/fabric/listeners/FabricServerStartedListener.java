package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class FabricServerStartedListener implements ServerLifecycleEvents.ServerStarted {
    FabricMain plugin = FabricMain.getInstance();

    @Override
    public void onServerStarted(MinecraftServer server) {
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
