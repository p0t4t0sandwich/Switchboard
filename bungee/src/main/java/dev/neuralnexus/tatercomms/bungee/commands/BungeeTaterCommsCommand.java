package dev.neuralnexus.tatercomms.bungee.commands;

import dev.neuralnexus.tatercomms.common.commands.TaterCommsCommand;
import dev.neuralnexus.taterlib.bungee.abstractions.player.BungeePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeTaterCommsCommand extends Command {
    public BungeeTaterCommsCommand() {
        super(TaterCommsCommand.getCommandName());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            // Check if sender is a player
            boolean isPlayer = sender instanceof ProxiedPlayer;
            BungeePlayer player = isPlayer ? new BungeePlayer((ProxiedPlayer) sender) : null;

            // Execute command
            TaterCommsCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
