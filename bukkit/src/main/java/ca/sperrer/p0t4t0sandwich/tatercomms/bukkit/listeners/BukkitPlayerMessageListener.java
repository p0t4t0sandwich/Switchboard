package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.BukkitMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BukkitPlayerMessageListener implements Listener {
    BukkitMain plugin = BukkitMain.getInstance();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        try {
            // Get player and message
            Player player = event.getPlayer();
            String message = event.getMessage();

            // Send message to message relay
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
