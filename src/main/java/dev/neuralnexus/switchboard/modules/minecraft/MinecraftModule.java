package dev.neuralnexus.switchboard.modules.minecraft;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.switchboard.modules.minecraft.command.SwitchboardCommand;
import dev.neuralnexus.switchboard.modules.minecraft.listeners.player.SwitchboardPlayerListener;
import dev.neuralnexus.switchboard.modules.minecraft.listeners.server.SwitchboardServerListener;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.event.api.ServerEvents;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** Minecraft module. */
public class MinecraftModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String name() {
        return "Minecraft";
    }

    @Override
    public void start() {
        if (STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!Switchboard.hasReloaded()) {
            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    event ->
                            event.registerCommand(
                                    Switchboard.plugin(), new SwitchboardCommand(), "tc"));

            // Register player listeners
            if (!TaterAPIProvider.serverType().isProxy()) {
                PlayerEvents.ADVANCEMENT_FINISHED.register(
                        SwitchboardPlayerListener::onPlayerAdvancementFinished);
                PlayerEvents.DEATH.register(SwitchboardPlayerListener::onPlayerDeath);
            }
            PlayerEvents.LOGIN.register(SwitchboardPlayerListener::onPlayerLogin);
            PlayerEvents.LOGOUT.register(SwitchboardPlayerListener::onPlayerLogout);
            PlayerEvents.MESSAGE.register(SwitchboardPlayerListener::onPlayerMessage);

            // Register server listeners
            ServerEvents.STARTED.register(SwitchboardServerListener::onServerStarted);
            ServerEvents.STOPPED.register(SwitchboardServerListener::onServerStopped);

            // TODO: Might be useful
            // if (commsMessage.isGlobal() || commsMessage.isRemote()) {
            //     player.sendMessage(commsMessage.applyPlaceHolders());
            // } else if (SwitchboardConfig.formattingEnabled() ||
            // !player.getServerName().equals(commsMessage.getSender().getServerName())) {
            //     player.sendMessage(commsMessage.applyPlaceHolders());
            // }

            // Register Switchboard events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    event -> {
                        Message message = event.getMessage();

                        // Prevents re-sending the message on the originating server
                        if (!SwitchboardConfigLoader.config().checkModule("formatting")
                                // Null check for proxies without any accessible servers
                                && message.sender().server() != null
                                && message.sender()
                                        .server()
                                        .name()
                                        .equals(TaterAPIProvider.get().getServer().name())) {
                            return;
                        }

                        // Return if the message is not a player message
                        if (!message.channel().equals(Message.MessageType.PLAYER_MESSAGE)) {
                            return;
                        }

                        // If the message formatting is enabled, send the message to all online
                        // players on the message's originating server
                        if (SwitchboardConfigLoader.config().checkModule("formatting")) {
                            TaterAPIProvider.get().getServer().onlinePlayers().stream()
                                    .filter(
                                            player ->
                                                    player.server()
                                                            .name()
                                                            .equals(
                                                                    message.sender()
                                                                            .server()
                                                                            .name()))
                                    .forEach(
                                            player ->
                                                    player.sendMessage(
                                                            message.applyPlaceHolders()));
                        } else {
                            // Send the message to the player
                            TaterAPIProvider.get()
                                    .getServer()
                                    .broadcastMessage(message.applyPlaceHolders());
                        }
                    });
        }
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;
    }
}
