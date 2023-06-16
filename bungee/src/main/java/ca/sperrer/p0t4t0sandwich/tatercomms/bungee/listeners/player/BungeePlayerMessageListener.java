package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.BungeeMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.player.BungeeTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeePlayerMessageListener implements Listener, PlayerMessageListener {
    BungeeMain plugin = BungeeMain.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMessage(ChatEvent event) {
        runTaskAsync(() -> {
            try {
                // If cancelled, or is a command, ignore
                if (event.isCancelled()
                        || event.isCommand()
                        || event.isProxyCommand()
                        // Check if sender is a player
                        || !(event.getSender() instanceof ProxiedPlayer)) {
                    return;
                }

                // Get player, message and server
                ProxiedPlayer player = (ProxiedPlayer) event.getSender();
                String server = player.getServer().getInfo().getName();
                String message = event.getMessage();

                // Send message to all other players, except those on the same server
                plugin.getProxy().getPlayers().stream()
                        .filter(p -> !p.getServer().getInfo().getName().equals(server))
                        .forEach(p -> p.sendMessage(new ComponentBuilder(String.format("[%s] %s: %s", server, player.getName(), message)).create()));

                // Send message to message relay
                taterPlayerMessage(new BungeeTaterPlayer(player), message);
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
