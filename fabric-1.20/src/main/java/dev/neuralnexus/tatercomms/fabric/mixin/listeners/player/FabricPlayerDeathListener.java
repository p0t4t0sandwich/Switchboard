package dev.neuralnexus.tatercomms.fabric.mixin.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerDeathListener;
import dev.neuralnexus.tatercomms.fabric.player.FabricTaterPlayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for player deaths and sends them to the message relay.
 */
@Mixin(ServerPlayerEntity.class)
public class FabricPlayerDeathListener implements PlayerDeathListener {
    /**
     * Called when a player dies.
     * @param source The source of the damage.
     * @param ci The callback info.
     */
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onPlayerDeath(DamageSource source, CallbackInfo ci) {
        // Send death message to message relay
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        taterPlayerDeath(new FabricTaterPlayer(player), source.getDeathMessage(player).getString());
    }
}
