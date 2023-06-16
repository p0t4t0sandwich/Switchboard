package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class ForgePlayerLogoutListener extends PlayerLogoutListener {
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
