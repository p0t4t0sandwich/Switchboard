package dev.neuralnexus.tatercomms.modules.proxy;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.api.message.MessageSender;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.NetworkEvents;
import dev.neuralnexus.taterlib.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.plugin.PluginModule;

import java.util.HashMap;

/** Proxy module. */
public class ProxyModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String name() {
        return "Proxy";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!TaterComms.hasReloaded()) {
            // Register player listeners
            if (TaterAPIProvider.serverType().isProxy()) {
                PlayerEvents.SERVER_SWITCH.register(
                        event -> {
                            SimplePlayer player = event.player();

                            // Construct and send two messages
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    new MessageSender(player, event.fromServer()),
                                                    Message.MessageType.PLAYER_LOGOUT,
                                                    player.name(),
                                                    TaterCommsConfigLoader.config()
                                                            .formatting()
                                                            .logout(),
                                                    new HashMap<>())));
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    player,
                                                    Message.MessageType.PLAYER_LOGIN,
                                                    player.name(),
                                                    TaterCommsConfigLoader.config()
                                                            .formatting()
                                                            .login(),
                                                    new HashMap<>())));
                        });
            }

            // Register plugin channels
            NetworkEvents.REGISTER_PLUGIN_MESSAGES.register(
                    event -> event.registerPluginChannels(Message.MessageType.getTypes()));
            NetworkEvents.SERVER_PLUGIN_MESSAGE.register(
                    event ->
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(Message.fromByteArray(event.data()))));

            // Register TaterComms message listener
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    event -> {
                        Message message = event.getMessage();
                        Message.MessageType channel = message.channel();

                        // Send the message using proxy channels
                        if (!TaterAPIProvider.serverType().isProxy()
                                && !channel.equals(Message.MessageType.PLAYER_MESSAGE)
                                && !channel.equals(Message.MessageType.SERVER_STARTED)
                                && !channel.equals(Message.MessageType.SERVER_STOPPED)) {
                            message.sender().sendPluginMessage(message);
                        }
                    });
        }

        TaterComms.logger().info("Submodule " + name() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects

        TaterComms.logger().info("Submodule " + name() + " has been stopped!");
    }
}
