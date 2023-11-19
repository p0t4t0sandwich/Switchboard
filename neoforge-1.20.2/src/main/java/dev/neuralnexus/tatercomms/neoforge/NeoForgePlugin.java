package dev.neuralnexus.tatercomms.neoforge;

import com.mojang.logging.LogUtils;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;
import dev.neuralnexus.taterlib.neoforge.logger.NeoForgeLogger;

import net.neoforged.fml.common.Mod;

/** NeoForge entry point. */
@Mod(TaterComms.Constants.PROJECT_ID)
public class NeoForgePlugin implements TaterCommsPlugin {
    public NeoForgePlugin() {
        ServerEvents.STOPPED.register(event -> pluginStop());
        pluginStart(this, new NeoForgeLogger(LogUtils.getLogger()));
    }
}
