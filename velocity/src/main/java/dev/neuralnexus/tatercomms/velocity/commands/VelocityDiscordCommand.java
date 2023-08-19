package dev.neuralnexus.tatercomms.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.velocity.abstractions.player.VelocityPlayer;

public class VelocityDiscordCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        try {
            String[] args = invocation.arguments();

            // Check if sender is a player
            boolean isPlayer = invocation.source() instanceof Player;
            VelocityPlayer player = isPlayer ? new VelocityPlayer((Player) invocation.source()) : null;

            // Execute command
            DiscordCommand.executeCommand(player, isPlayer, args);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
