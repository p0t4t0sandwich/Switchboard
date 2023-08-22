package dev.neuralnexus.tatercomms.forge.commands;

import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.forge.ForgeTaterCommsPlugin;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.abstrations.player.ForgePlayer;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.command.Commands.literal;
import static net.minecraft.command.Commands.argument;

/**
 * Forge implementation of the Discord command.
 */
@Mod.EventBusSubscriber(modid = ForgeTaterCommsPlugin.MOD_ID)
public final class ForgeDiscordCommand {
    /**
     * Registers the Discord command.
     * @param event The register commands event.
     */
    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent event) {
        int permissionLevel;
        if (event.getEnvironment() == Commands.EnvironmentType.DEDICATED) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        // Register command
        event.getDispatcher().register(literal(DiscordCommand.getCommandName())
                .requires(source -> source.hasPermission(permissionLevel))
                .then(argument("command", StringArgumentType.greedyString())
                    .executes(context -> {
                        try {
                            String[] args = context.getArgument("command", String.class).split(" ");

                            // Check if sender is a player
                            boolean isPlayer = context.getSource().getEntity() instanceof PlayerEntity;
                            ForgePlayer player = isPlayer ? new ForgePlayer((PlayerEntity) context.getSource().getEntity()) : null;

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
