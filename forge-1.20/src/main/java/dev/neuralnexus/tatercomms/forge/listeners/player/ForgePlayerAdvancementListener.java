package dev.neuralnexus.tatercomms.forge.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerAdvancementListener;
import dev.neuralnexus.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraft.advancements.DisplayInfo;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for player advancement and sends it to the message relay.
 */
public class ForgePlayerAdvancementListener implements PlayerAdvancementListener {
    /**
     * Called when a player advances.
     * @param event The advancement event
     */
    @SubscribeEvent
    public void onPlayerAdvancement(AdvancementEvent event) {
        DisplayInfo display = event.getAdvancement().getDisplay();
        if (display != null && display.shouldAnnounceChat()) {
            // Send advancement to message relay
            taterPlayerAdvancement(new ForgeTaterPlayer(event.getEntity()), display.getTitle().getString());
        }
    }
}
