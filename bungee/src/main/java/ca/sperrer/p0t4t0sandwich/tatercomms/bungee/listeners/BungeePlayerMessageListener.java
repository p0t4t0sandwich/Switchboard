package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.BungeeMain;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeePlayerMessageListener implements Listener {
    BungeeMain plugin = BungeeMain.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMessage(ChatEvent event) {
        try {
            // Get sender
            Connection sender = event.getSender();
            String message = event.getMessage();

            // Ignore console
            if (sender == null) {
                return;

            // Ignore commands
            } else if (message.startsWith("/")) {
                return;

            // Ignore empty messages
            } else if (message.isEmpty()) {
                return;
            }

            // Get player and server
            ProxiedPlayer player = (ProxiedPlayer) sender;
            String server = player.getServer().getInfo().getName();

            // Send message to all other players, except those on the same server
            plugin.getProxy().getPlayers().stream()
                    .filter(p -> !p.getServer().getInfo().getName().equals(server))
                    .forEach(p -> p.sendMessage(new ComponentBuilder(String.format("[%s] %s: %s", server, player.getName(), message)).create()));

            // Send message to message relay
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
