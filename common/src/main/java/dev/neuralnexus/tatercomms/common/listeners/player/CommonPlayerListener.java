package dev.neuralnexus.tatercomms.common.listeners.player;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;

public interface CommonPlayerListener {
    /**
     * Called when a player makes an advancement, and sends it to the message relay.
     * @param args The player and advancement.
     */
    static void onPlayerAdvancementFinished(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String advancement = (String) args[1];
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier(), advancement));
    }

    /**
     * Called when a player dies, and sends it to the message relay.
     * @param args The player and death message.
     */
    static void onPlayerDeath(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String deathMessage = (String) args[1];
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_DEATH, deathMessage));
    }

    /**
     * Called when a player logs in, and sends it to the message relay.
     * @param args The player.
     */
    static void onPlayerLogin(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_LOGIN, player.getName() + " joined the game"));
    }

    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param args The player.
     */
    static void onPlayerLogout(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_LOGOUT, player.getName() + " left the game"));
    }

    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param args The player, message, and whether the message was cancelled.
     */
    static void onPlayerMessage(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String message = (String) args[1];
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_MESSAGE, message));
    }

    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param args The player and the server they switched from.
     */
    static void onPlayerServerSwitch(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String fromServer = (String) args[1];

        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        // Construct and send two messages
        relay.relayMessage(new CommsMessage(new CommsSender(player, fromServer), CommsMessage.MessageType.PLAYER_LOGOUT, player.getName() + " left the game"));
        relay.relayMessage(new CommsMessage(player, CommsMessage.MessageType.PLAYER_LOGIN, player.getName() + " joined the game"));
    }
}
