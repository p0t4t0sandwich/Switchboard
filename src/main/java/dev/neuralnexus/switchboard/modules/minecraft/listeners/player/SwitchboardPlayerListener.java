package dev.neuralnexus.switchboard.modules.minecraft.listeners.player;

import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterlib.event.player.*;
import dev.neuralnexus.taterlib.player.SimplePlayer;

import java.util.HashMap;

/** Listeners for player events. */
public interface SwitchboardPlayerListener {
    /**
     * Called when a player finishes an advancement.
     *
     * @param event The event.
     */
    static void onPlayerAdvancementFinished(PlayerAdvancementEvent.AdvancementFinished event) {
        SimplePlayer player = event.player();
        String advancement = event.advancement();
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("advancement", advancement);
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_ADVANCEMENT_FINISHED,
                                advancement,
                                SwitchboardConfigLoader.config().formatting().advancement(),
                                placeholders)));
    }

    /**
     * Called when a player dies.
     *
     * @param event The event.
     */
    static void onPlayerDeath(PlayerDeathEvent event) {
        SimplePlayer player = event.player();
        String deathMessage = event.deathMessage();
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("deathmessage", deathMessage);
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_DEATH,
                                deathMessage,
                                SwitchboardConfigLoader.config().formatting().death(),
                                placeholders)));
    }

    /**
     * Called when a player logs in.
     *
     * @param event The event.
     */
    static void onPlayerLogin(PlayerLoginEvent event) {
        SimplePlayer player = event.player();
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_LOGIN,
                                player.name(),
                                SwitchboardConfigLoader.config().formatting().login(),
                                new HashMap<>())));
    }

    /**
     * Called when a player logs out.
     *
     * @param event The event.
     */
    static void onPlayerLogout(PlayerLogoutEvent event) {
        SimplePlayer player = event.player();
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_LOGOUT,
                                player.name(),
                                SwitchboardConfigLoader.config().formatting().logout(),
                                new HashMap<>())));
    }

    /**
     * Called when a player sends a message.
     *
     * @param event The event.
     */
    static void onPlayerMessage(PlayerMessageEvent event) {
        SimplePlayer player = event.player();
        String message = event.message();
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                player,
                                Message.MessageType.PLAYER_MESSAGE,
                                message,
                                SwitchboardConfigLoader.config().formatting().local(),
                                new HashMap<>())));
    }
}
