package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bukkit.logger.BukkitLogger;
import dev.neuralnexus.taterlib.common.event.api.ServerEvents;

import org.bukkit.plugin.java.JavaPlugin;

/** Bukkit entry point. */
public class BukkitPlugin extends JavaPlugin implements TaterCommsPlugin {
    public BukkitPlugin() {
        ServerEvents.STOPPED.register(event -> pluginStop());
        pluginStart(this, new BukkitLogger(getLogger()));
    }
}
