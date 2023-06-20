package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;
import static net.minecraft.server.command.CommandManager.literal;

public final class FabricDiscordCommand implements DiscordCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean environment) {
        dispatcher.register(literal(DiscordCommand.getCommandName())
            .requires(source -> source.hasPermissionLevel(0))
            .executes(context -> {
                runTaskAsync(() -> {
                    try {
                        String[] args = new String[] {context.getArgument(DiscordCommand.getCommandName(), String.class)};

                        // Send message to player or console
                        Entity entity = context.getSource().getEntity();
                        if (entity instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity) entity).sendMessage(Text.of(DiscordCommand.executeCommand(args)), false);
                        } else {
                            TaterComms.useLogger((ansiiParser(DiscordCommand.executeCommand(args))));
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                        e.printStackTrace();
                    }
                });
                return 1;
            })
        );
    }
}
