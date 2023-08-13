package dev.neuralnexus.tatercomms.neoforge.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;
import dev.neuralnexus.tatercomms.neoforge.ForgeMain;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

/**
 * Listens for server starting and starts TaterComms.
 */
public class ForgeServerStartingListener {
    ForgeMain plugin = ForgeMain.getInstance();

    /**
     * Called when the server starts.
     * @param event The server starting event
     */
    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        try {
            // Start TaterComms
            plugin.taterComms = new TaterComms("config", ForgeMain.logger);
            plugin.taterComms.start();

            // Register LuckPerms hook
            if (ModList.get().isLoaded("luckperms")) {
                TaterComms.useLogger("[TaterComms] LuckPerms detected, enabling LuckPerms hook.");
                TaterComms.addHook(new LuckPermsHook());
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
