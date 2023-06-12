package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.BungeeMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeePlayerLogoutListener implements Listener {
    BungeeMain plugin = BungeeMain.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent event) {
        runTaskAsync(() -> {
            try {
                // Get the MessageRelay instance
                MessageRelay relay = plugin.taterComms.getMessageRelay();
                String server = event.getPlayer().getServer().getInfo().getName(); // plugin.taterComms.getServerName();

                // Create a TaterPlayer object
                BungeeTaterPlayer taterPlayer = new BungeeTaterPlayer(event.getPlayer());

                // Add the TaterPlayer to the cache
                relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);

                // Relay the join message
                relay.sendPlayerLogout(taterPlayer, server);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
