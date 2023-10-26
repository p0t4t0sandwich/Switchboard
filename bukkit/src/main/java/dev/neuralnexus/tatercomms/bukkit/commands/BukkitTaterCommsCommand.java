package dev.neuralnexus.tatercomms.bukkit.commands;

import dev.neuralnexus.tatercomms.common.commands.TaterCommsCommand;
import dev.neuralnexus.taterlib.bukkit.abstractions.player.BukkitPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitTaterCommsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // Check if sender is a player
            boolean isPlayer = sender instanceof Player;
            BukkitPlayer player = isPlayer ? new BukkitPlayer((Player) sender) : null;

            // Execute command
            TaterCommsCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
