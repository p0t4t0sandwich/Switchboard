package ca.sperrer.p0t4t0sandwich.tatercomms.forge.listeners;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.PlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.forge.player.ForgeTaterPlayer;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePlayerAdvancementListener extends PlayerAdvancementListener {
    @SubscribeEvent
    public void onPlayerAdvancement(AdvancementEvent event) {
        Player player = event.getEntity();
        Advancement advancement = event.getAdvancement();
        if (advancement.getDisplay() != null && advancement.getDisplay().shouldAnnounceChat()) {
            taterPlayerAdvancement(new ForgeTaterPlayer(player), advancement.getDisplay().getTitle().getString());
        }
    }
}
