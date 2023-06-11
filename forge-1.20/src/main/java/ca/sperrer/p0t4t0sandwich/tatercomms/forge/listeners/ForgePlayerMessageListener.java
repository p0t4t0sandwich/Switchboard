package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.forge.ForgeMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePlayerMessageListener {
    ForgeMain mod = ForgeMain.getInstance();

    @SubscribeEvent
    void onPlayerMessage(ServerChatEvent event) {
        try {
            // Get player and message
            ServerPlayer player = event.getPlayer();
            String message = event.getMessage().getString();
            String server = mod.taterComms.getServerName();

            // Get taterPlayer
            ForgeTaterPlayer taterPlayer = new ForgeTaterPlayer(player);

            // Send message to message relay
            mod.taterComms.getMessageRelay().sendMessage(taterPlayer, server, message);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
