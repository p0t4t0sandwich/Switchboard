package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.message.SentMessage.Chat.class)
public class FabricPlayerMessageListener implements PlayerMessageListener {
    @Shadow @Final private SignedMessage message;

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    public void send(ServerPlayerEntity sender, boolean filterMaskEnabled, MessageType.Parameters params, CallbackInfo ci) {
        // Send message to message relay
        taterPlayerMessage(new FabricTaterPlayer(sender), message.getSignedContent());
        ci.cancel();
    }
}
