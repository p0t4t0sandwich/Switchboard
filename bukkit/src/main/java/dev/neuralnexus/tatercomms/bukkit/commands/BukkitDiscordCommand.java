package dev.neuralnexus.tatercomms.bukkit.commands;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitDiscordCommand implements CommandExecutor, DiscordCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // Check if sender is a player
            if ((sender instanceof Player)) {
                Player player = (Player) sender;

                // Permission check
                if (!player.hasPermission(DiscordCommand.getCommandPermission())) {
                    player.sendMessage("§cYou do not have permission to use this command.");
                    return true;
                }
                player.sendMessage(DiscordCommand.executeCommand(args));

            } else {
                TaterComms.useLogger(Utils.ansiiParser(DiscordCommand.executeCommand(args)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
