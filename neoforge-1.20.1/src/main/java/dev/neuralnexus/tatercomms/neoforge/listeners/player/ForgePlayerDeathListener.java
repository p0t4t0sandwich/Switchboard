package dev.neuralnexus.tatercomms.neoforge.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerDeathListener;
import dev.neuralnexus.tatercomms.neoforge.player.ForgeTaterPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for player death and sends it to the message relay.
 */
public class ForgePlayerDeathListener implements PlayerDeathListener {
    /**
     * Called when a player dies.
     * @param event The player death event
     */
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        // Send death message through relay
        taterPlayerDeath(new ForgeTaterPlayer((Player) entity), event.getSource().getLocalizedDeathMessage(entity).getString());
    }
}
