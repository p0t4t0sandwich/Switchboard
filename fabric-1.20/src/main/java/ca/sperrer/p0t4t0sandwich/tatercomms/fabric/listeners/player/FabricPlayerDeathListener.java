package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerDeathListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Listens for player deaths and sends them to the message relay.
 */
public class FabricPlayerDeathListener implements ServerLivingEntityEvents.AfterDeath, PlayerDeathListener {
    /**
     * Called when a player dies.
     * @param entity The entity.
     * @param damageSource The damage source.
     */
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayerEntity)) return;
        // Send death message to message relay
        taterPlayerDeath(new FabricTaterPlayer((ServerPlayerEntity) entity), damageSource.getDeathMessage(entity).getString());
    }
}
