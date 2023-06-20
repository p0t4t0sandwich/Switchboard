package ca.sperrer.p0t4t0sandwich.tatercomms.fabric.mixin.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.listeners.server.ServerStartedListener;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for server start and sends it to the message relay.
 */
@Mixin(MinecraftServer.class)
public class FabricServerStartedListener implements ServerStartedListener {
    /**
     * Called when the server starts.
     * @param ci The callback info.
     */
    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setFavicon(Lnet/minecraft/server/ServerMetadata;)V", ordinal = 0))
    public void onServerStarted(CallbackInfo ci) {
        // Send server started to message relay
        taterServerStarted();
    }
}
