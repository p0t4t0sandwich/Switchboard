package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class FabricPlayerLoginListener implements ServerPlayConnectionEvents.Join, PlayerLoginListener {
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        runTaskAsync(() -> {
            try {
                // Pass TaterPlayer to helper function
                taterPlayerLogin(new FabricTaterPlayer(handler.getPlayer()));
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
