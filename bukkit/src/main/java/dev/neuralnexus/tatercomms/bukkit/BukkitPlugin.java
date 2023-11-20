package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bukkit.logger.BukkitLogger;

import org.bukkit.plugin.java.JavaPlugin;

/** Bukkit entry point. */
public class BukkitPlugin extends JavaPlugin implements TaterCommsPlugin {
    public BukkitPlugin() {
        pluginStart(this, new BukkitLogger(getLogger()));
    }
}
