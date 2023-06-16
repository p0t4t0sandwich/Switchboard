package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraft.advancements.DisplayInfo;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePlayerAdvancementListener implements PlayerAdvancementListener {
    @SubscribeEvent
    public void onPlayerAdvancement(AdvancementEvent event) {
        DisplayInfo display = event.getAdvancement().getDisplay();
        if (display != null && display.shouldAnnounceChat()) {
            // Send advancement to message relay
            taterPlayerAdvancement(new ForgeTaterPlayer(event.getEntity()), display.getTitle().getString());
        }
    }
}
