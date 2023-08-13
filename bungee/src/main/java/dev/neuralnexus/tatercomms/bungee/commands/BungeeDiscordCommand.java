package dev.neuralnexus.tatercomms.bungee.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeeDiscordCommand extends Command implements DiscordCommand {
    public BungeeDiscordCommand() {
        super(DiscordCommand.getCommandName());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof ProxiedPlayer)) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    // Permission check
                    if (!player.hasPermission(DiscordCommand.getCommandPermission())) {
                        player.sendMessage(new ComponentBuilder("§cYou do not have permission to use this command.").create());
                        return;
                    }
                    player.sendMessage(new ComponentBuilder(DiscordCommand.executeCommand(args)).create());

                } else {
                    sender.sendMessage(new ComponentBuilder(DiscordCommand.executeCommand(args)).create());
                }
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
