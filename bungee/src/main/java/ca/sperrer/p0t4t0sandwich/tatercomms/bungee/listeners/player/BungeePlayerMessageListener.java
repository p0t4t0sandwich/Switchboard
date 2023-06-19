package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

/**
 * Listens for player messages and sends them to the message relay.
 */
public class BungeePlayerMessageListener implements Listener, PlayerMessageListener {
    /**
     * Called when a player sends a message.
     * @param event The event.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMessage(ChatEvent event) {
        // If cancelled, or is a command, ignore
        if (event.isCancelled() || event.isCommand() || event.isProxyCommand()) return;
        event.setCancelled(true);

        // Get player, message and server
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String message = event.getMessage();

        // Send message to message relay
        taterPlayerMessage(new BungeeTaterPlayer(player), message, true);
    }
}
