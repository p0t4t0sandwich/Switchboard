package dev.neuralnexus.tatercomms.modules.minecraft;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.modules.minecraft.command.TaterCommsCommand;
import dev.neuralnexus.tatercomms.modules.minecraft.listeners.player.TaterCommsPlayerListener;
import dev.neuralnexus.tatercomms.modules.minecraft.listeners.server.TaterCommsServerListener;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.event.api.PlayerEvents;
import dev.neuralnexus.taterlib.event.api.ServerEvents;
import dev.neuralnexus.taterlib.plugin.Module;

/**
 * Minecraft module.
 */
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
                    event ->
                            event.registerCommand(
                                    TaterComms.getPlugin(), new TaterCommsCommand(), "tc"));

            // Register player listeners
            PlayerEvents.ADVANCEMENT_FINISHED.register(
                    TaterCommsPlayerListener::onPlayerAdvancementFinished);
            PlayerEvents.DEATH.register(TaterCommsPlayerListener::onPlayerDeath);
            PlayerEvents.LOGIN.register(TaterCommsPlayerListener::onPlayerLogin);
            PlayerEvents.LOGOUT.register(TaterCommsPlayerListener::onPlayerLogout);
            PlayerEvents.MESSAGE.register(TaterCommsPlayerListener::onPlayerMessage);

            // Register server listeners
            ServerEvents.STARTED.register(TaterCommsServerListener::onServerStarted);
            // TODO: Find the null pointer here
//            ServerEvents.STOPPED.register(TaterCommsServerListener::onServerStopped);
            ServerEvents.STOPPING.register(TaterCommsServerListener::onServerStopped);

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
                        if (!TaterCommsConfig.formattingEnabled()
                                && message.getSender()
                                .getServerName()
                                .equals(TaterCommsAPIProvider.get().getServerName())) {
                            return;
                        }

                        // Return if the message is not a player message
                        if (!message.getChannel().equals(Message.MessageType.PLAYER_MESSAGE.id())) {
                            return;
                        }

                        TaterAPIProvider.get()
                                .getServer()
                                .getOnlinePlayers()
                                .forEach(player -> player.sendMessage(message.applyPlaceHolders()));
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
