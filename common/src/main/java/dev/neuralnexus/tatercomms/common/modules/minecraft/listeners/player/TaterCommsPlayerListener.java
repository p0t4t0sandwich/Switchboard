package dev.neuralnexus.tatercomms.common.modules.minecraft.listeners.player;

import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.api.message.Message;
import dev.neuralnexus.tatercomms.common.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.common.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.common.event.player.*;
import dev.neuralnexus.taterlib.common.player.Player;

import java.util.HashMap;

/** Listeners for player events. */
public interface TaterCommsPlayerListener {
    /**
     * Called when a player finishes an advancement.
     *
     * @param event The event.
     */
    static void onPlayerAdvancementFinished(PlayerAdvancementEvent.AdvancementFinished event) {
        Player player = event.getPlayer();
        String advancement = event.getAdvancement();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsAPIProvider.get().getServerName());
        }
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("advancement", advancement);
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier(),
                                advancement,
                                TaterCommsAPIProvider.get().getFormatting("advancement"),
                                placeholders)));
    }

    /**
     * Called when a player dies.
     *
     * @param event The event.
     */
    static void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String deathMessage = event.getDeathMessage();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsAPIProvider.get().getServerName());
        }
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("deathmessage", deathMessage);
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_DEATH,
                                deathMessage,
                                TaterCommsAPIProvider.get().getFormatting("death"),
                                placeholders)));
    }

    /**
     * Called when a player logs in.
     *
     * @param event The event.
     */
    static void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsAPIProvider.get().getServerName());
        }
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_LOGIN,
                                player.getName(),
                                TaterCommsAPIProvider.get().getFormatting("login"),
                                new HashMap<>())));
    }

    /**
     * Called when a player logs out.
     *
     * @param event The event.
     */
    static void onPlayerLogout(PlayerLogoutEvent event) {
        Player player = event.getPlayer();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsAPIProvider.get().getServerName());
        }
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_LOGOUT,
                                player.getName(),
                                TaterCommsAPIProvider.get().getFormatting("logout"),
                                new HashMap<>())));
    }

    /**
     * Called when a player sends a message.
     *
     * @param event The event.
     */
    static void onPlayerMessage(PlayerMessageEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (player.getServerName().equals("local")) {
            player.setServerName(TaterCommsAPIProvider.get().getServerName());
        }
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_MESSAGE,
                                message,
                                TaterCommsAPIProvider.get().getFormatting("local"),
                                new HashMap<>())));
    }
}
