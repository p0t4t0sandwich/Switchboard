package dev.neuralnexus.tatercomms.velocity.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class VelocityDiscordCommand implements SimpleCommand, DiscordCommand {
    @Override
    public void execute(Invocation invocation) {
//        runTaskAsync(() -> {
            try {
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
                    TaterComms.useLogger(ansiiParser(DiscordCommand.executeCommand(args)));
                }
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
//        });
    }
}
