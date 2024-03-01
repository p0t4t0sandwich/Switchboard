/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.discord;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.switchboard.modules.discord.api.DiscordServer;
import dev.neuralnexus.switchboard.modules.discord.command.DiscordCommand;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** Discord module. */
public class DiscordModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String name() {
        return "Discord";
    }

    @Override
    public void start() {
        if (STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        // Check if the token and channel mappings are set
        String token = SwitchboardConfigLoader.config().discord().token();
        if (token == null || token.isEmpty()) {
            Switchboard.logger().info("No Discord token found in switchboard.conf!");
            return;
        }
        if (SwitchboardConfigLoader.config().discord().mappings().isEmpty()) {
            Switchboard.logger().info("No server-channel mappings found in switchboard.conf!");
            return;
        }

        if (!Switchboard.hasReloaded()) {
            // Register events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    (event) -> {
                        // Prevents discord messages from being passed back to discord
                        if (!(event.getMessage().sender().server() instanceof DiscordServer)) {
                            SwitchboardAPIProvider.get()
                                    .discordAPI()
                                    .sendMessage(event.getMessage());
                        }
                    });

            // Register commands
            CommandEvents.REGISTER_COMMAND.register(
                    (event -> event.registerCommand(Switchboard.plugin(), new DiscordCommand())));
        }

        // Start the bot
        SwitchboardAPIProvider.get().discordAPI().startBot();
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        SwitchboardAPIProvider.get().discordAPI().removeBot();
    }
}
