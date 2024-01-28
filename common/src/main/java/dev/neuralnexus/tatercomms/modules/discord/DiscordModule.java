package dev.neuralnexus.tatercomms.modules.discord;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.modules.discord.command.DiscordCommand;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.plugin.Module;

/**
 * Discord module.
 */
public class DiscordModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "Discord";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Check if the token and channel mappings are set
            if (TaterCommsConfig.DiscordConfig.token() == null
                    || TaterCommsConfig.DiscordConfig.token().isEmpty()) {
                TaterComms.getLogger().info("No Discord token found in tatercomms.config.yml!");
                return;
            }
            if (TaterCommsConfig.DiscordConfig.channels().isEmpty()) {
                TaterComms.getLogger()
                        .info("No server-channel mappings found in tatercomms.config.yml!");
                return;
            }

            // Register events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    (event) -> {
                        // Prevents discord messages from being passed back to discord
                        if (!event.getMessage().getSender().getServerName().equals("discord")) {
                            TaterCommsAPIProvider.get()
                                    .discordAPI()
                                    .sendMessage(event.getMessage());
                        }
                    });

            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    (event -> event.registerCommand(TaterComms.getPlugin(), new DiscordCommand())));
        }

        // Start the bot
        TaterCommsAPIProvider.get().discordAPI().startBot();

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
        TaterCommsAPIProvider.get().discordAPI().removeBot();

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
