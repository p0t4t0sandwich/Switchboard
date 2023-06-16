package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;
import ca.sperrer.p0t4t0sandwich.tatercomms.fabric.player.FabricTaterPlayer;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

@Mixin(PlayerAdvancementTracker.class)
public abstract class FabricPlayerAdvancementListener {
    @Shadow private ServerPlayerEntity owner;
    @Shadow public abstract AdvancementProgress getProgress(Advancement advancement);

    @Inject(method = "endTrackingCompleted", at = @At("HEAD"))
    public void onEndTrackingCompleted(Advancement advancement, CallbackInfo ci) {
        runTaskAsync(() -> {
            try {
                if (advancement.getDisplay() != null && getProgress(advancement).isDone()) {
                    // Get player
                    FabricTaterPlayer taterPlayer = new FabricTaterPlayer(this.owner);

                    // Get advancement title
                    String advancementTitle = advancement.getDisplay().getTitle().getString();

                    // Send message to relay
                    MessageRelay relay = MessageRelay.getInstance();
                    relay.setTaterPlayerInCache(taterPlayer.getUUID(), taterPlayer);
                    relay.sendMessage(taterPlayer, taterPlayer.getServerName(), "has made the advancement [" + advancementTitle + "]");
                }
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
