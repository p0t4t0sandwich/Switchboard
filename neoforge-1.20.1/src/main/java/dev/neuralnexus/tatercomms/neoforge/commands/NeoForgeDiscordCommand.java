package dev.neuralnexus.tatercomms.neoforge.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.neoforge.NeoForgeTaterCommsPlugin;
import dev.neuralnexus.taterlib.common.commands.TaterLibCommand;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.neoforge.abstractions.player.NeoForgePlayer;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * NeoForge implementation of the Discord command.
 */
@Mod.EventBusSubscriber(modid = NeoForgeTaterCommsPlugin.MOD_ID)
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

        // Register command
        event.getDispatcher().register(literal(TaterLibCommand.getCommandName())
                .requires(source -> source.hasPermission(permissionLevel))
                .then(argument("command", StringArgumentType.greedyString())
                        .executes(context -> {
                            try {
                                String[] args = context.getArgument("command", String.class).split(" ");

                                // Check if sender is a player
                                boolean isPlayer = context.getSource().getEntity() instanceof Player;
                                NeoForgePlayer player = isPlayer ? new NeoForgePlayer((Player) context.getSource().getEntity()) : null;

                                // Execute command
                                DiscordCommand.executeCommand(player, isPlayer, args);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return 0;
                            }
                            return 1;
                        })
                ));
    }
}
