package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.FabricMain;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public final class FabricTemplateCommand {
    private static final FabricMain plugin = FabricMain.getInstance();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean environment) {
        dispatcher.register(literal("template")
            .requires(source -> source.hasPermissionLevel(0))
            .then(argument("pronouns", StringArgumentType.greedyString())
            .executes(context -> {
                runTaskAsync(() -> {
                    try {
                        String[] args = new String[] {context.getArgument("pronouns", String.class)};

                        // Send message to player or console
                        Entity entity = context.getSource().getEntity();
                        if (entity instanceof ServerPlayerEntity) {
                            String text = "";
                            ((ServerPlayerEntity) entity).sendMessage(Text.of(text), false);
                        } else {
                            plugin.logger.info(ansiiParser("§cYou must be a player to use this command."));
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                        e.printStackTrace();
                    }
                });
                return 1;
            })
        ));
    }
}
