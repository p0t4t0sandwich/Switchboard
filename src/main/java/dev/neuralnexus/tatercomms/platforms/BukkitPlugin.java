package dev.neuralnexus.tatercomms.platforms;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsPlugin;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/** Bukkit entry point. */
public class BukkitPlugin extends JavaPlugin implements TaterCommsPlugin {
    public BukkitPlugin() {
        pluginStart(
                this,
                Bukkit.getServer(),
                Bukkit.getLogger(),
                new LoggerAdapter(TaterComms.Constants.PROJECT_NAME, Bukkit.getLogger()));
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}
