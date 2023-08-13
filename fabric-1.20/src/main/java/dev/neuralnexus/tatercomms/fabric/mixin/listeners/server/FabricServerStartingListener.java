package dev.neuralnexus.tatercomms.fabric.mixin.listeners.server;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;
import dev.neuralnexus.tatercomms.fabric.FabricMain;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Listens for server start and starts TaterComms.
 */
@Mixin(MinecraftServer.class)
public class FabricServerStartingListener {
    FabricMain plugin = FabricMain.getInstance();
    /**
     * Called when the server is starting.
     * @param ci The callback info.
     */
    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"))
    public void onServerStarting(CallbackInfo ci) {
        // Start TaterComms
        plugin.taterComms = new TaterComms("config", plugin.logger);
        plugin.taterComms.start();

        // Register LuckPerms hook
        if (FabricLoader.getInstance().isModLoaded("luckperms")) {
            TaterComms.useLogger("[TaterComms] LuckPerms detected, enabling LuckPerms hook.");
            TaterComms.addHook(new LuckPermsHook());
        }
    }
}