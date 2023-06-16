package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player.VelocityTaterPlayer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class VelocityPlayerLogoutListener implements PlayerLogoutListener {
    @Subscribe
    public void onPlayerLogout(DisconnectEvent event) {
        runTaskAsync(() -> {
            try {
                // Pass TaterPlayer to helper function
                taterPlayerLogout(new VelocityTaterPlayer(event.getPlayer()));
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
