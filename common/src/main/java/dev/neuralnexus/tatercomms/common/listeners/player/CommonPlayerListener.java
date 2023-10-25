package dev.neuralnexus.tatercomms.common.listeners.player;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;

import java.util.HashMap;

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
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("advancement", advancement);
        relay.relayMessage(new CommsMessage(player,
                        CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier(),
                        advancement,
                        TaterCommsConfig.formattingChat().get("advancement"),
                        placeholders
        ));
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
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("deathmessage", deathMessage);
        relay.relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_DEATH,
                deathMessage,
                TaterCommsConfig.formattingChat().get("death"),
                placeholders
        ));
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
        relay.relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_LOGIN,
                player.getName(),
                TaterCommsConfig.formattingChat().get("login"),
                new HashMap<>()
        ));
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
        relay.relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_LOGOUT,
                player.getName(),
                TaterCommsConfig.formattingChat().get("logout"),
                new HashMap<>()
        ));
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
        relay.relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_MESSAGE,
                message,
                TaterCommsConfig.formattingChat().get("local"),
                new HashMap<>()
        ));
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
        relay.relayMessage(new CommsMessage(new CommsSender(player, fromServer),
                CommsMessage.MessageType.PLAYER_LOGOUT,
                player.getName(),
                TaterCommsConfig.formattingChat().get("logout"),
                new HashMap<>()
        ));
        relay.relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_LOGIN,
                player.getName(),
                TaterCommsConfig.formattingChat().get("login"),
                new HashMap<>()
        ));
    }
}
