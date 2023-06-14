package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerLoginListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeePlayerLoginListener extends PlayerLoginListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(ServerSwitchEvent event) {
        runTaskAsync(() -> {
            try {
                // If player is switching servers, don't run this function
                if (event.getFrom() != null) {
                    return;
                }

                // Get Player and current server
                ProxiedPlayer player = event.getPlayer();
                String toServer = event.getPlayer().getServer().getInfo().getName();

                BungeeTaterPlayer taterPlayer = new BungeeTaterPlayer(player);
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
