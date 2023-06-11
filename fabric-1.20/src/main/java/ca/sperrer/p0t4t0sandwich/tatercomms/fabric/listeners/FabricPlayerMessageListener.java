package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricPlayerMessageListener implements ServerMessageEvents.ChatMessage {
    FabricMain mod = FabricMain.getInstance();

    @Override
    public void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        try {
            // Get player and message
            ServerPlayerEntity player = sender;
            String msg = message.getSignedContent();
            String server = mod.taterComms.getServerName();

            // Get taterPlayer
            FabricTaterPlayer taterPlayer = new FabricTaterPlayer(player);

            // Send message to message relay
            mod.taterComms.getMessageRelay().sendMessage(taterPlayer, server, msg);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
