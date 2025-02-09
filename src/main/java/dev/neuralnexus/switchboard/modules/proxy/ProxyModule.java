/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.proxy;

import dev.neuralnexus.modapi.metadata.MetaAPI;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.api.message.MessageSender;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterapi.entity.player.User;
import dev.neuralnexus.taterapi.event.api.NetworkEvents;
import dev.neuralnexus.taterapi.event.api.PlayerEvents;
import dev.neuralnexus.taterapi.loader.plugin.PluginModule;

import java.util.HashMap;

/** Proxy module. */
public class ProxyModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String id() {
        return "Proxy";
    }

    @Override
    public void onEnable() {
        if (STARTED) {
            SwitchboardPlugin.logger().info("Submodule " + id() + " has already started!");
            return;
        }
        STARTED = true;

        if (!SwitchboardPlugin.hasReloaded()) {
            // Register player listeners
            if (MetaAPI.instance().isProxy()) {
                PlayerEvents.SERVER_SWITCH.register(
                        event -> {
                            User player = event.player();

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
            NetworkEvents.REGISTER_CHANNELS.register(
                    event -> event.register(Message.MessageType.getTypes()));
            NetworkEvents.S2P_CUSTOM_PACKET.register(
                    event ->
                            SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(
                                            Message.fromByteArray(event.packet().data()))));

            // Register Switchboard message listener
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    event -> {
                        Message message = event.getMessage();
                        Message.MessageType channel = message.channel();

                        // Send the message using proxy channels
                        if (!MetaAPI.instance().isProxy()
                                && !channel.equals(Message.MessageType.PLAYER_MESSAGE)
                                && !channel.equals(Message.MessageType.SERVER_STARTED)
                                && !channel.equals(Message.MessageType.SERVER_STOPPED)) {
                            message.sender().sendPacket(message);
                        }
                    });
        }

        SwitchboardPlugin.logger().info("Submodule " + id() + " has been started!");
    }

    @Override
    public void onDisable() {
        if (!STARTED) {
            SwitchboardPlugin.logger().info("Submodule " + id() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects

        SwitchboardPlugin.logger().info("Submodule " + id() + " has been stopped!");
    }
}
