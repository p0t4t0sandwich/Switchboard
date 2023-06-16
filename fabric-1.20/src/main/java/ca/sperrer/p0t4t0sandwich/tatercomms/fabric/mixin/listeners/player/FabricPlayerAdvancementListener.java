package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.listeners.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.player.PlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
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

@Mixin(PlayerAdvancementTracker.class)
public abstract class FabricPlayerAdvancementListener implements PlayerAdvancementListener {
    @Shadow private ServerPlayerEntity owner;
    @Shadow public abstract AdvancementProgress getProgress(Advancement advancement);

    @Inject(method = "endTrackingCompleted", at = @At("HEAD"))
    public void onPlayerAdvancement(Advancement advancement, CallbackInfo ci) {
        AdvancementDisplay display = advancement.getDisplay();
        if (display != null && display.shouldAnnounceToChat() && getProgress(advancement).isDone()) {
            // Send advancement to message relay
            taterPlayerAdvancement(new FabricTaterPlayer(this.owner), display.getTitle().getString());
        }
    }
}
