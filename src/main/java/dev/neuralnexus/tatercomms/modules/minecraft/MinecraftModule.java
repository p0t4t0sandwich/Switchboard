package dev.neuralnexus.tatercomms.modules.minecraft;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.modules.minecraft.command.TaterCommsCommand;
import dev.neuralnexus.tatercomms.modules.minecraft.listeners.player.TaterCommsPlayerListener;
import dev.neuralnexus.tatercomms.modules.minecraft.listeners.server.TaterCommsServerListener;
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
            TaterComms.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!TaterComms.hasReloaded()) {
            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    event ->
                            event.registerCommand(
                                    TaterComms.plugin(), new TaterCommsCommand(), "tc"));

            // Register player listeners
            if (!TaterAPIProvider.serverType().isProxy()) {
                PlayerEvents.ADVANCEMENT_FINISHED.register(
                        TaterCommsPlayerListener::onPlayerAdvancementFinished);
                PlayerEvents.DEATH.register(TaterCommsPlayerListener::onPlayerDeath);
            }
            PlayerEvents.LOGIN.register(TaterCommsPlayerListener::onPlayerLogin);
            PlayerEvents.LOGOUT.register(TaterCommsPlayerListener::onPlayerLogout);
            PlayerEvents.MESSAGE.register(TaterCommsPlayerListener::onPlayerMessage);

            // Register server listeners
            ServerEvents.STARTED.register(TaterCommsServerListener::onServerStarted);
            ServerEvents.STOPPED.register(TaterCommsServerListener::onServerStopped);

            // TODO: Might be useful
            // if (commsMessage.isGlobal() || commsMessage.isRemote()) {
            //     player.sendMessage(commsMessage.applyPlaceHolders());
            // } else if (TaterCommsConfig.formattingEnabled() ||
            // !player.getServerName().equals(commsMessage.getSender().getServerName())) {
            //     player.sendMessage(commsMessage.applyPlaceHolders());
            // }

            // Register TaterComms events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    event -> {
                        Message message = event.getMessage();

                        // Prevents re-sending the message on the originating server
                        if (!TaterCommsConfigLoader.config().checkModule("formatting")
                                // Null check for proxies without any accessible servers
                                && message.getSender().server() != null
                                && message.getSender()
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
                        if (TaterCommsConfigLoader.config().checkModule("formatting")) {
                            TaterAPIProvider.get().getServer().onlinePlayers().stream()
                                    .filter(
                                            player ->
                                                    player.server()
                                                            .name()
                                                            .equals(
                                                                    message.getSender()
                                                                            .server()
                                                                            .name()))
                                    .forEach(
                                            player ->
                                                    player.sendMessage(
                                                            message.applyPlaceHolders()));
                        }
                    });
        }
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;
    }
}
