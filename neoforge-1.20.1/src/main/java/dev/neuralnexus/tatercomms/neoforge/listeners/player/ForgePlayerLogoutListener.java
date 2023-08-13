package dev.neuralnexus.tatercomms.neoforge.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import dev.neuralnexus.tatercomms.neoforge.player.ForgeTaterPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for player logout and sends it to the message relay.
 */
public class ForgePlayerLogoutListener implements PlayerLogoutListener {
    /**
     * Called when a player logs out.
     * @param event The player logout event
     */
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // Pass TaterPlayer to helper function
        taterPlayerLogout(new ForgeTaterPlayer(event.getEntity()));
    }
}
