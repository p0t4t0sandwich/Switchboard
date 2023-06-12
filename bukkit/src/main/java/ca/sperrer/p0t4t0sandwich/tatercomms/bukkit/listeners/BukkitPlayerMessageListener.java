package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.BukkitMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BukkitPlayerMessageListener implements Listener {
    BukkitMain plugin = BukkitMain.getInstance();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        runTaskAsync(() -> {
            try {
                // Get player and message
                Player player = event.getPlayer();
                String message = event.getMessage();
                String server = plugin.taterComms.getServerName();

                BukkitTaterPlayer taterPlayer = new BukkitTaterPlayer(player);

                // Send message to message relay
                plugin.taterComms.getMessageRelay().sendMessage(taterPlayer, server, message);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
