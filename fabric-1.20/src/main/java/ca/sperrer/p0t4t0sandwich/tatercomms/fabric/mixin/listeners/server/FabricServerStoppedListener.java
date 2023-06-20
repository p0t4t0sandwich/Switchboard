package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStoppedListener;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for server stop and sends it to the message relay.
 */
@Mixin(MinecraftServer.class)
public class FabricServerStoppedListener implements ServerStoppedListener {
    /**
     * Called when the server stops.
     * @param ci The callback info.
     */
    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onServerStopped(CallbackInfo ci) {
        // Send server stopped to message relay
        taterServerStopped();
    }
}
