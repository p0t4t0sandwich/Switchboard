package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.BukkitMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BukkitPlayerLogoutListener implements Listener {
    BukkitMain plugin = BukkitMain.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        runTaskAsync(() -> {
            try {
                // Get the MessageRelay instance
                MessageRelay relay = plugin.taterComms.getMessageRelay();
                String server = plugin.taterComms.getServerName();

                // Create a TaterPlayer object
                BukkitTaterPlayer taterPlayer = new BukkitTaterPlayer(event.getPlayer());

                // Relay the quit message
                relay.sendPlayerLogout(taterPlayer, server);

                // Add the TaterPlayer to the cache
                relay.removeTaterPlayerFromCache(taterPlayer.getUUID());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
