package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for player logouts and sends them to the message relay.
 */
@Mixin(ServerPlayerEntity.class)
public class FabricPlayerLogoutListener implements PlayerLogoutListener {
    /**
     * Called when a player logs out.
     * @param ci The callback info.
     */
    @Inject(method = "onDisconnect", at = @At("HEAD"))
    public void onPlayerDeath(CallbackInfo ci) {
        // Send death message to message relay
        taterPlayerLogout(new FabricTaterPlayer((ServerPlayerEntity) (Object) this));
    }
}
