package dev.neuralnexus.tatercomms.neoforge.commands;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.commands.Commands.literal;

/**
 * Registers the discord command.
 */
public final class NeoForgeDiscordCommand implements DiscordCommand {
    /**
     * Registers the command.
     * @param event The event.
     */
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        int permissionLevel;
        if (event.getCommandSelection() == Commands.CommandSelection.DEDICATED) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        event.getDispatcher().register(literal(DiscordCommand.getCommandName())
                .requires(source -> source.hasPermission(permissionLevel))
                .executes(context -> {
                    String[] args = new String[]{};

                    // Send message to player or console
                    Entity entity = context.getSource().getEntity();
                    if (entity instanceof ServerPlayer) {
                        ((ServerPlayer) entity).displayClientMessage(Component.empty().append(DiscordCommand.executeCommand(args)), false);
                    } else {
                        TaterComms.useLogger(Utils.ansiiParser(DiscordCommand.executeCommand(args)));
                    }
                    return 1;
                })
        );
    }
}
