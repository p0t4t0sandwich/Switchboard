package dev.neuralnexus.tatercomms.common.listeners.player;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.taterlib.common.event.player.*;
import dev.neuralnexus.taterlib.common.player.Player;

import java.util.HashMap;

/**
 * Listeners for player events.
 */
public interface TaterCommsPlayerListener {
    /**
     * Called when a player finishes an advancement.
     * @param event The event.
     */
    static void onPlayerAdvancementFinished(PlayerAdvancementEvent.AdvancementFinished event) {
        Player player = event.getPlayer();
        String advancement = event.getAdvancement();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("advancement", advancement);
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(player,
                        CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier(),
                        advancement,
                        TaterCommsConfig.formattingChat().get("advancement"),
                        placeholders
        ));
    }

    /**
     * Called when a player dies.
     * @param event The event.
     */
    static void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String deathMessage = event.getDeathMessage();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("deathmessage", deathMessage);
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_DEATH,
                deathMessage,
                TaterCommsConfig.formattingChat().get("death"),
                placeholders
        ));
    }

    /**
     * Called when a player logs in.
     * @param event The event.
     */
    static void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_LOGIN,
                player.getName(),
                TaterCommsConfig.formattingChat().get("login"),
                new HashMap<>()
        ));
    }

    /**
     * Called when a player logs out.
     * @param event The event.
     */
    static void onPlayerLogout(PlayerLogoutEvent event) {
        Player player = event.getPlayer();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_LOGOUT,
                player.getName(),
                TaterCommsConfig.formattingChat().get("logout"),
                new HashMap<>()
        ));
    }

    /**
     * Called when a player sends a message.
     * @param event The event.
     */
    static void onPlayerMessage(PlayerMessageEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsConfig.serverName());
        }
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(player,
                CommsMessage.MessageType.PLAYER_MESSAGE,
                message,
                TaterCommsConfig.formattingChat().get("local"),
                new HashMap<>()
        ));
    }

    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param event The event.
     */
    static void onPlayerServerSwitch(PlayerServerSwitchEvent event) {
        Player player = event.getPlayer();
        String fromServer = event.getFromServer();

        CommsRelay relay = TaterComms.getMessageRelay();
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
