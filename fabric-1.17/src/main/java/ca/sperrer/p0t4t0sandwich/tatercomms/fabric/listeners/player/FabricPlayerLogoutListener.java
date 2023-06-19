package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

/**
 * Listens for player logouts and sends them to the message relay.
 */
public class FabricPlayerLogoutListener implements ServerPlayConnectionEvents.Disconnect, PlayerLogoutListener {
    /**
     * Called when a player logs out.
     * @param handler The player's network handler
     * @param server The server
     */
    @Override
    public void onPlayDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        // Pass TaterPlayer to helper function
        taterPlayerLogout(new FabricTaterPlayer(handler.getPlayer()));
    }
}
