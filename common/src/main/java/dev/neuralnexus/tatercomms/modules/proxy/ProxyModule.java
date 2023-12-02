package dev.neuralnexus.tatercomms.modules.proxy;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.api.message.MessageSender;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.event.api.PluginMessageEvents;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.plugin.Module;

import java.util.HashMap;

/** Proxy module. */
public class ProxyModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "Proxy";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Register player listeners
            if (TaterAPIProvider.get().serverType().isProxy()) {
                PlayerEvents.SERVER_SWITCH.register(
                        event -> {
                            Player player = event.getPlayer();
                            String fromServer = event.getFromServer();

                            // Construct and send two messages
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    new MessageSender(player, fromServer),
                                                    Message.MessageType.PLAYER_LOGOUT,
                                                    player.getName(),
                                                    TaterCommsAPIProvider.get()
                                                            .getFormatting("logout"),
                                                    new HashMap<>())));
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    player,
                                                    Message.MessageType.PLAYER_LOGIN,
                                                    player.getName(),
                                                    TaterCommsAPIProvider.get()
                                                            .getFormatting("login"),
                                                    new HashMap<>())));
                        });
            }

            // Register plugin channels
            PluginMessageEvents.REGISTER_PLUGIN_MESSAGES.register(
                    event -> event.registerPluginChannels(Message.MessageType.getTypes()));

            // Register plugin message listener
            PluginMessageEvents.SERVER_PLUGIN_MESSAGE.register(
                    event ->
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            Message.fromByteArray(event.getData()))));

            // Register TaterComms message listener
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    event -> {
                        Message message = event.getMessage();

                        // Send the message using proxy channels
                        if (!TaterAPIProvider.get().serverType().isProxy()
                                && !event.getMessage()
                                        .getChannel()
                                        .equals(Message.MessageType.PLAYER_MESSAGE.id())) {
                            message.getSender().sendPluginMessage(message);
                        }
                    });
        }

        TaterComms.getLogger().info("Submodule " + getName() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects

        TaterComms.getLogger().info("Submodule " + getName() + " has been stopped!");
    }

    @Override
    public void reload() {
        if (!STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        TaterComms.getLogger().info("Submodule " + getName() + " has been reloaded!");
    }
}
