package dev.neuralnexus.switchboard.modules.proxy;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.api.message.MessageSender;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
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
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!Switchboard.hasReloaded()) {
            // Register player listeners
            if (TaterAPIProvider.serverType().isProxy()) {
                PlayerEvents.SERVER_SWITCH.register(
                        event -> {
                            SimplePlayer player = event.player();

                            // Construct and send two messages
                            SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    new MessageSender(player, event.fromServer()),
                                                    Message.MessageType.PLAYER_LOGOUT,
                                                    player.name(),
                                                    SwitchboardConfigLoader.config()
                                                            .formatting()
                                                            .logout(),
                                                    new HashMap<>())));
                            SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            new Message(
                                                    player,
                                                    Message.MessageType.PLAYER_LOGIN,
                                                    player.name(),
                                                    SwitchboardConfigLoader.config()
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
                            SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(Message.fromByteArray(event.data()))));

            // Register Switchboard message listener
            SwitchboardEvents.RECEIVE_MESSAGE.register(
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

        Switchboard.logger().info("Submodule " + name() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects

        Switchboard.logger().info("Submodule " + name() + " has been stopped!");
    }
}
