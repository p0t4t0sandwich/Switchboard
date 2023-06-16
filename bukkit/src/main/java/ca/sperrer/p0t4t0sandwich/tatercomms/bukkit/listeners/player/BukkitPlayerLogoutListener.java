package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BukkitPlayerLogoutListener implements Listener, PlayerLogoutListener {
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        runTaskAsync(() -> {
            try {
                // Pass TaterPlayer to helper function
                taterPlayerLogout(new BukkitTaterPlayer(event.getPlayer()));
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
