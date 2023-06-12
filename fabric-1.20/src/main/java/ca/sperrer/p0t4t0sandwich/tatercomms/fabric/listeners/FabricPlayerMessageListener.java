package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricPlayerMessageListener extends PlayerMessageListener implements ServerMessageEvents.ChatMessage {
    @Override
    public void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        try {
            // Send message to message relay
            taterPlayerMessage(new FabricTaterPlayer(sender), message.getSignedContent());
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
