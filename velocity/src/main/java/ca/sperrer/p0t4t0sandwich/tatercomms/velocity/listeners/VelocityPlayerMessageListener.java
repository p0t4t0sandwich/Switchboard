package ca.sperrer.p0t4t0sandwich.tatercomms.velocity.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.VelocityMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.velocity.player.VelocityTaterPlayer;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

public class VelocityPlayerMessageListener extends PlayerMessageListener {
    VelocityMain plugin = VelocityMain.getInstance();

    @Subscribe(order = PostOrder.LAST)
    public void onPlayerMessage(PlayerChatEvent event) {
        // Get player and message
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!player.getCurrentServer().isPresent()) {
            return;
        }
        String server = player.getCurrentServer().get().getServerInfo().getName();

        // Send message to all other players, except those on the same server
        plugin.getServer().getAllPlayers().stream()
                .filter(p -> !p.getCurrentServer().isPresent() || !p.getCurrentServer().get().getServerInfo().getName().equals(server))
                .forEach(p -> p.sendMessage(Component.text(String.format("[%s] %s: %s", server, player.getUsername(), message))));

        // Send message to message relay
        taterPlayerMessage(new VelocityTaterPlayer(player), message);
    }
}
