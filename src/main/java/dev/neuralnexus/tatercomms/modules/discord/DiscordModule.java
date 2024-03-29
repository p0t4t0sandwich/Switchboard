package dev.neuralnexus.tatercomms.modules.discord;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.modules.discord.api.DiscordAPI;
import dev.neuralnexus.tatercomms.modules.discord.command.DiscordCommand;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.plugin.PluginModule;
import dev.neuralnexus.taterlib.server.SimpleServer;

/** Discord module. */
public class DiscordModule implements PluginModule {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String name() {
        return "Discord";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Check if the token and channel mappings are set
            String token = TaterCommsConfigLoader.config().discord().token();
            if (token == null || token.isEmpty()) {
                TaterComms.logger().info("No Discord token found in tatercomms.conf!");
                return;
            }
            if (TaterCommsConfigLoader.config().discord().mappings().isEmpty()) {
                TaterComms.logger().info("No server-channel mappings found in tatercomms.conf!");
                return;
            }

            // Register events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    (event) -> {
                        // Prevents discord messages from being passed back to discord
                        if (!(event.getMessage().getSender().server()
                                instanceof DiscordAPI.DiscordServer)) {
                            TaterCommsAPIProvider.get()
                                    .discordAPI()
                                    .sendMessage(event.getMessage());
                        }
                    });

            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    (event -> event.registerCommand(TaterComms.plugin(), new DiscordCommand())));
        }

        // Start the bot
        TaterCommsAPIProvider.get().discordAPI().startBot();

        TaterComms.logger().info("Submodule " + name() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects
        TaterCommsAPIProvider.get().discordAPI().removeBot();

        TaterComms.logger().info("Submodule " + name() + " has been stopped!");
    }

    @Override
    public void reload() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        TaterComms.logger().info("Submodule " + name() + " has been reloaded!");
    }
}
