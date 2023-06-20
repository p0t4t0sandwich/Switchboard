package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BukkitDiscordCommand implements CommandExecutor, DiscordCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AtomicBoolean success = new AtomicBoolean(false);
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof Player)) {
                    Player player = (Player) sender;

                    // Permission check
                    if (!player.hasPermission(DiscordCommand.getCommandPermission())) {
                        player.sendMessage("§cYou do not have permission to use this command.");
                        return;
                    }
                    player.sendMessage(DiscordCommand.executeCommand(args));

                } else {
                    TaterComms.useLogger(ansiiParser(DiscordCommand.executeCommand(args)));
                }
                success.set(true);
            } catch (Exception e) {
                success.set(false);
                System.err.println(e);
                e.printStackTrace();
            }
        });
        return success.get();
    }
}
