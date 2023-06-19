package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLoginListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listens for player logins and sends them to the message relay.
 */
public class BukkitPlayerLoginListener implements Listener, PlayerLoginListener {
    /**
     * Called when a player logs in.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        // Pass TaterPlayer to helper function
        taterPlayerLogin(new BukkitTaterPlayer(event.getPlayer()));
    }
}
