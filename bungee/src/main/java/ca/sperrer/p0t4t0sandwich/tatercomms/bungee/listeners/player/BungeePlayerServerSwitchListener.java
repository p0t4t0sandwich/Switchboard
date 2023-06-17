package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerServerSwitchListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player server switches and sends them to the message relay.
 */
public class BungeePlayerServerSwitchListener implements Listener, PlayerServerSwitchListener {
    /**
     * Called when a player switches servers.
     * @param event The event.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerSwitch(ServerSwitchEvent event) {
        runTaskAsync(() -> {
            try {
                // If player is just joining, don't run this function
                if (event.getFrom() == null) {
                    return;
                }

                // Get Player UUID and current server
                ProxiedPlayer player = event.getPlayer();
                String toServer = player.getServer().getInfo().getName();

                // Pass Player UUID and current server to helper function
                taterServerSwitch(new BungeeTaterPlayer(player), toServer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
