package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Listens for player messages and sends them to the message relay.
 */
public class FabricPlayerMessageListener implements ServerMessageEvents.ChatMessage, PlayerMessageListener {
    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param message the broadcast message with message decorators applied; use {@code message.getSignedContent()} to get the text
     * @param sender  the player that sent the message
     * @param params the {@link MessageType.Parameters}
     */
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
