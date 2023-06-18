package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerDeathListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Listens for player deaths and sends them to the message relay.
 */
public class BukkitPlayerDeathListener implements Listener, PlayerDeathListener {
    /**
     * Called when a player dies.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Send death message through relay
        taterPlayerDeath(new BukkitTaterPlayer(event.getEntity()), event.getDeathMessage());
    }
}
