package dev.neuralnexus.tatercomms.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.Utils;
import net.kyori.adventure.text.Component;

public class VelocityDiscordCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
            String[] args = invocation.arguments();

            // Check if sender is a player
            if ((invocation.source() instanceof Player)) {
                Player player = (Player) invocation.source();

                // Permission check
                if (!player.hasPermission(DiscordCommand.getCommandPermission())) {
                    player.sendMessage(Component.text("§cYou do not have permission to use this command."));
                    return;
                }
                player.sendMessage(Component.text(DiscordCommand.executeCommand(args)));
            } else {
                TaterComms.useLogger(Utils.ansiiParser(DiscordCommand.executeCommand(args)));
            }
    }
}
