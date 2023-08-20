package dev.neuralnexus.tatercomms.fabric.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.commands.TaterLibCommand;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.fabric.abstractions.player.FabricPlayer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * The Fabric implementation of the Discord command.
 */
public class FabricDiscordCommand {
    /**
     * Registers the command.
     * @param dispatcher The command dispatcher.
     * @param dedicated Whether the server is dedicated or not.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        int permissionLevel;
        if (dedicated) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        dispatcher.register(literal(TaterLibCommand.getCommandName())
                .requires(source -> source.hasPermissionLevel(permissionLevel))
                .then(argument("command", StringArgumentType.greedyString())
                    .executes(context -> {
                        try {
                            String[] args = context.getArgument("command", String.class).split(" ");

                            // Check if sender is a player
                            boolean isPlayer = context.getSource().getEntity() instanceof ServerPlayerEntity;
                            FabricPlayer player = isPlayer ? new FabricPlayer((ServerPlayerEntity) context.getSource().getEntity()) : null;

                            // Execute command
                            DiscordCommand.executeCommand(player, isPlayer, args);
                        } catch (Exception e) {
                            System.err.println(e);
                            e.printStackTrace();
                            return 0;
                        }
                        return 1;
                    })
        ));
    }
}
