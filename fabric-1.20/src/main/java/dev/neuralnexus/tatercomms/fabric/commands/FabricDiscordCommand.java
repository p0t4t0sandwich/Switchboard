package dev.neuralnexus.tatercomms.fabric.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;
public class FabricDiscordCommand {
    /**
     * Registers the command.
     * @param dispatcher The command dispatcher.
     * @param registryAccess The command registry access.
     * @param environment The command registration environment.
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        int permissionLevel;
        if (environment.name().equals("DEDICATED")) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        dispatcher.register(literal(DiscordCommand.getCommandName())
                .requires(source -> source.hasPermissionLevel(permissionLevel))
                .executes(context -> {
                    String[] args = new String[]{};

                    // Send message to player or console
                    Entity entity = context.getSource().getEntity();
                    if (entity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) entity).sendMessage(Text.of(DiscordCommand.executeCommand(args)), false);
                    } else {
                        TaterComms.useLogger((Utils.ansiiParser(DiscordCommand.executeCommand(args))));
                    }
                    return 1;
                })
        );
    }

}
