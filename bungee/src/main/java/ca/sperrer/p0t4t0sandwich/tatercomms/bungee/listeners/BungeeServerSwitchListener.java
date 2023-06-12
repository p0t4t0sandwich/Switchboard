package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeeServerSwitchListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerSwitch(ServerSwitchEvent event) {
        runTaskAsync(() -> {
            try {
                // Get the MessageRelay instance
                MessageRelay relay = MessageRelay.getInstance();

                // Get the player and server names
                ProxiedPlayer player = event.getPlayer();
                String fromServer = event.getFrom().getName();
                String toServer = player.getServer().getInfo().getName();

                // Get the TaterPlayer object
                BungeeTaterPlayer taterPlayer = (BungeeTaterPlayer) relay.getTaterPlayerFromCache(player.getUniqueId());

                // Update the server name and TaterPlayer object
                taterPlayer.setServerName(toServer);
                relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

                // Relay the server switch message
                relay.sendPlayerServerSwitch(taterPlayer, fromServer, toServer);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
