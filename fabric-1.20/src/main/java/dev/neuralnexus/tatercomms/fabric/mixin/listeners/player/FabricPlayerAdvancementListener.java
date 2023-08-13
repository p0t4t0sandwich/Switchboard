package dev.neuralnexus.tatercomms.fabric.mixin.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerAdvancementListener;
import dev.neuralnexus.tatercomms.fabric.player.FabricTaterPlayer;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for player advancements and sends them to the message relay.
 */
@Mixin(PlayerAdvancementTracker.class)
public abstract class FabricPlayerAdvancementListener implements PlayerAdvancementListener {
    @Shadow private ServerPlayerEntity owner;
    @Shadow public abstract AdvancementProgress getProgress(Advancement advancement);

    /**
     * Called when a player completes an advancement.
     * @param advancement The advancement
     * @param ci Callback info
     */
    @Inject(method = "endTrackingCompleted", at = @At("HEAD"))
    public void onPlayerAdvancement(Advancement advancement, CallbackInfo ci) {
        AdvancementDisplay display = advancement.getDisplay();
        if (display != null && display.shouldAnnounceToChat() && getProgress(advancement).isDone()) {
            // Send advancement to message relay
            taterPlayerAdvancement(new FabricTaterPlayer(this.owner), display.getTitle().getString());
        }
    }
}
