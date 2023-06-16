package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class ForgePlayerLoginListener extends PlayerLoginListener {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        runTaskAsync(() -> {
            try {
                // Pass TaterPlayer to helper function
                taterPlayerLogin(new ForgeTaterPlayer(event.getEntity()));
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
