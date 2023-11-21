package dev.neuralnexus.tatercomms.common.modules.minecraft;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.api.message.Message;
import dev.neuralnexus.tatercomms.common.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.common.modules.Module;
import dev.neuralnexus.tatercomms.common.modules.minecraft.command.TaterCommsCommand;
import dev.neuralnexus.tatercomms.common.modules.minecraft.listeners.player.TaterCommsPlayerListener;
import dev.neuralnexus.tatercomms.common.modules.minecraft.listeners.server.TaterCommsServerListener;
import dev.neuralnexus.taterlib.common.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.common.event.api.CommandEvents;
import dev.neuralnexus.taterlib.common.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;

/** Minecraft module. */
public class MinecraftModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "Minecraft";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    (event ->
                            event.registerCommand(
                                    TaterComms.getPlugin(), new TaterCommsCommand(), "tc")));

            // Register player listeners
            PlayerEvents.ADVANCEMENT_FINISHED.register(
                    TaterCommsPlayerListener::onPlayerAdvancementFinished);
            PlayerEvents.DEATH.register(TaterCommsPlayerListener::onPlayerDeath);
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
                    (event) -> {
                        // Skips server start/stop messages
                        if (event.getMessage()
                                        .getChannel()
                                        .equals(Message.MessageType.SERVER_STARTED.getIdentifier())
                                || event.getMessage()
                                        .getChannel()
                                        .equals(
                                                Message.MessageType.SERVER_STOPPED
                                                        .getIdentifier())) {
                            return;
                        }

                        // Prevents re-sending the message on the originating server
                        if (!TaterCommsConfig.formattingEnabled()
                                && event.getMessage()
                                        .getSender()
                                        .getServerName()
                                        .equals(TaterCommsAPIProvider.get().getServerName())) {
                            return;
                        }

                        TaterAPIProvider.get()
                                .getServer()
                                .getOnlinePlayers()
                                .forEach(
                                        (player) ->
                                                player.sendMessage(
                                                        event.getMessage().applyPlaceHolders()));
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
