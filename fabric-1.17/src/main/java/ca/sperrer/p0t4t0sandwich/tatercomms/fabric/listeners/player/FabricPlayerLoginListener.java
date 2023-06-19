package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

/**
 * Listens for player logins and sends them to the message relay.
 */
public class FabricPlayerLoginListener implements ServerPlayConnectionEvents.Join, PlayerLoginListener {
    /**
     * Called when a player logs in.
     * @param handler The player's network handler
     * @param sender The packet sender
     * @param server The server
     */
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        // Pass TaterPlayer to helper function
        taterPlayerLogin(new FabricTaterPlayer(handler.getPlayer()));
    }
}
