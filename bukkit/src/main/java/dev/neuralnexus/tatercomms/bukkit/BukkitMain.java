package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.bukkit.commands.BukkitDiscordCommand;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.common.Utils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the Bukkit plugin.
 */
public class BukkitMain extends JavaPlugin {
    public TaterComms taterComms;

    // Singleton instance
    private static BukkitMain instance;
    public static BukkitMain getInstance() {
        return instance;
    }

    /**
     * Returns the server type.
     * @return The server type.
     */
    public String getServerType() {
        return Utils.getBukkitServerType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("TaterComms is running on " + getServerType() + ".");

        // Start
        taterComms = new TaterComms("plugins", getLogger());
        taterComms.start();

        // Register commands
        getCommand(DiscordCommand.getCommandName()).setExecutor(new BukkitDiscordCommand());

        // Plugin enable message
        getLogger().info("TaterComms has been enabled!");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
