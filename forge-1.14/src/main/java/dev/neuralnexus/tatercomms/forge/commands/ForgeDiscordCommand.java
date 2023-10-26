package dev.neuralnexus.tatercomms.forge.commands;

import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.forge.ForgeTaterCommsPlugin;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.abstrations.player.ForgePlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.loading.FMLLoader;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

/**
 * Forge implementation of the Discord command.
 */
@Mod.EventBusSubscriber(modid = ForgeTaterCommsPlugin.MOD_ID)
public final class ForgeDiscordCommand {
    /**
     * Registers the command.
     * @param event The register commands event.
     */
    @SubscribeEvent
    public static void registerCommand(FMLServerStartingEvent event) {
        int permissionLevel;
        if (FMLLoader.getDist().isDedicatedServer()) {
            // Check if LuckPerms is hooked
            permissionLevel = LuckPermsHook.isHooked() ? 0 : 4;
        } else {
            permissionLevel = 0;
        }

        // Register command
        event.getCommandDispatcher().register(literal(DiscordCommand.getCommandName())
                .requires(source -> source.hasPermissionLevel(permissionLevel))
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
