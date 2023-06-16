package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BukkitPlayerMessageListener implements Listener, PlayerMessageListener {
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        runTaskAsync(() -> {
            try {
                // Send message to message relay
                taterPlayerMessage(new BukkitTaterPlayer(event.getPlayer()), event.getMessage());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
