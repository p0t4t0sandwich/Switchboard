package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.ServerSwitchListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;

import java.util.UUID;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class VelocityServerSwitchListener extends ServerSwitchListener {
    @Subscribe
    public void onServerSwitch(ServerConnectedEvent event) {
        runTaskAsync(() -> {
            try {
                // Get Player UUID and current server
                Player player = event.getPlayer();
                UUID playerUUID = player.getUniqueId();

                String toServer;
                if (!player.getCurrentServer().isPresent()) {
                    toServer = "null";
                } else {
                    toServer = player.getCurrentServer().get().getServerInfo().getName();
                }

                // Pass Player UUID and current server to helper function
                taterServerSwitch(playerUUID, toServer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
