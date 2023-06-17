package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player.VelocityTaterPlayer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player login and sends it to the message relay.
 */
public class VelocityPlayerLoginListener implements PlayerLoginListener {
    /**
     * Called when a player logs in, and sends it to the message relay.
     * @param event The player login event
     */
    @Subscribe
    public void onPlayerLogin(ServerConnectedEvent event) {
        runTaskAsync(() -> {
            try {
                // If player is switching servers, don't run this function
                if (event.getPreviousServer().isPresent()) {
                    return;
                }

                // Get Player and current server
                Player player = event.getPlayer();
                String toServer = event.getServer().getServerInfo().getName();

                VelocityTaterPlayer taterPlayer = new VelocityTaterPlayer(player);
                taterPlayer.setServerName(toServer);

                // Pass TaterPlayer to helper function
                taterPlayerLogin(taterPlayer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
