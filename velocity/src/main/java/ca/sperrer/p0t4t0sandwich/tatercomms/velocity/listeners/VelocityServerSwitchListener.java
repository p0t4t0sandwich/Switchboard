package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.ServerSwitchListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player.VelocityTaterPlayer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class VelocityServerSwitchListener extends ServerSwitchListener {
    @Subscribe
    public void onServerSwitch(ServerConnectedEvent event) {
        runTaskAsync(() -> {
            try {
                // If player is just joining, don't run this function
                if (!event.getPreviousServer().isPresent()) {
                    return;
                }

                // Get Player and current server
                Player player = event.getPlayer();
                String toServer = event.getServer().getServerInfo().getName();

                // Pass Player and current server to helper function
                taterServerSwitch(new VelocityTaterPlayer(player), toServer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
