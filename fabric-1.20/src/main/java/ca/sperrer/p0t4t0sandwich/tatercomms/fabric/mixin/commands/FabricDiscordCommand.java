package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.ansiiParser;
import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;
import static net.minecraft.server.command.CommandManager.literal;

@Mixin(CommandManager.class)
public class FabricDiscordCommand implements DiscordCommand {
    @Shadow @Final private CommandDispatcher<ServerCommandSource> dispatcher;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"))
    private void registerDiscordCommand(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo ci) {
        this.dispatcher.register(literal(DiscordCommand.getCommandName())
                .requires(source -> source.hasPermissionLevel(0))
                .executes(context -> {
                    runTaskAsync(() -> {
                        try {
                            String[] args = new String[]{};

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
