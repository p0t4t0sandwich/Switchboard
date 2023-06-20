package ca.sperrer.p0t4t0sandwich.tatercomms.forge.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;
import static net.minecraft.commands.Commands.literal;

public final class ForgeDiscordCommand implements DiscordCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(literal(DiscordCommand.getCommandName())
            .requires(source -> source.hasPermission(0))
                .executes(context -> {
                    runTaskAsync(() -> {
                        try {
                            String[] args = new String[] {context.getArgument(DiscordCommand.getCommandName(), String.class)};

                            // Send message to player or console
                            Entity entity = context.getSource().getEntity();
                            if (entity instanceof ServerPlayer) {
                                ((ServerPlayer) entity).displayClientMessage(Component.empty().append(DiscordCommand.executeCommand(args)), false);
                            } else {
                                TaterComms.useLogger(ansiiParser(DiscordCommand.executeCommand(args)));
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
