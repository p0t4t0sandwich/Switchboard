package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class FabricServerStartedListener implements ServerLifecycleEvents.ServerStarted {
    FabricMain mod = FabricMain.getInstance();

    @Override
    public void onServerStarted(MinecraftServer server) {
        try {
            // Start LPPronouns
            mod.taterComms = new TaterComms("config", mod.logger);
            mod.taterComms.start();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
