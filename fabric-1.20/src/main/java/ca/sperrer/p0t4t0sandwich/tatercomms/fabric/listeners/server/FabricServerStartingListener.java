package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

/**
 * Listens for server start and sends it to the message relay.
 */
public class FabricServerStartingListener implements ServerLifecycleEvents.ServerStarting {
    FabricMain plugin = FabricMain.getInstance();

    /**
     * Called when the server is starting, and starts TaterComms.
     * @param server The server
     */
    @Override
    public void onServerStarting(MinecraftServer server) {
        try {
            // Start TaterComms
            plugin.taterComms = new TaterComms("config", plugin.logger);
            plugin.taterComms.start();

            // Register LuckPerms hook
            if (FabricLoader.getInstance().isModLoaded("luckperms")) {
                TaterComms.useLogger("LuckPerms detected, enabling LuckPerms hook.");
                TaterComms.addHook(new LuckPermsHook());
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
