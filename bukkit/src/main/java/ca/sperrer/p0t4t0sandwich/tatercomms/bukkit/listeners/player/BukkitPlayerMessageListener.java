package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.player.BukkitTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
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
        runTaskAsync(() -> {
            try {
                // TODO: Cancel event and send message to message relay, relay will format message and send it back to the server
                // Send message to message relay
                taterPlayerMessage(new BukkitTaterPlayer(event.getPlayer()), event.getMessage());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
