package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player messages and sends them to the message relay.
 */
public class BukkitPlayerMessageListener implements Listener, PlayerMessageListener {
    /**
     * Called when a player sends a message.
     * @param event The event.
     */
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        // TODO: Get weather or not the event should be cancelled from the message relay
        // Send message to message relay
        taterPlayerMessage(new BukkitTaterPlayer(event.getPlayer()), event.getMessage());
        event.setCancelled(true);
    }
}
