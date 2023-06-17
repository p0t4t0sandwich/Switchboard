package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player logout and sends it to the message relay.
 */
public class ForgePlayerLogoutListener implements PlayerLogoutListener {
    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param event The player logout event
     */
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        runTaskAsync(() -> {
            try {
                // Pass TaterPlayer to helper function
                taterPlayerLogout(new ForgeTaterPlayer(event.getEntity()));
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
