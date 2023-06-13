package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePlayerMessageListener extends PlayerMessageListener {
    @SubscribeEvent
    void onPlayerMessage(ServerChatEvent event) {
        try {
            // Send message to message relay
            taterPlayerMessage(new ForgeTaterPlayer(event.getPlayer()), event.getMessage().getString());
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
