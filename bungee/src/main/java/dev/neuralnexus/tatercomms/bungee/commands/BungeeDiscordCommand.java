package dev.neuralnexus.tatercomms.bungee.commands;

import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.bungee.abstractions.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeDiscordCommand extends Command implements DiscordCommand {
    public BungeeDiscordCommand() {
        super(DiscordCommand.getCommandName());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            // Check if sender is a player
            boolean isPlayer = sender instanceof ProxiedPlayer;
            BungeePlayer player = isPlayer ? new BungeePlayer((ProxiedPlayer) sender) : null;

            // Execute command
            DiscordCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
