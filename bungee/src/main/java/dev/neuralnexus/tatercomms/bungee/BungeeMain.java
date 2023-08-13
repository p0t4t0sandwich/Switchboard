package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.bungee.commands.BungeeDiscordCommand;
import dev.neuralnexus.tatercomms.common.TaterComms;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Main class for BungeeCord.
 */
public class BungeeMain extends Plugin {
    public TaterComms taterComms;

    /**
     * Gets the server type.
     * @return The server type.
     */
    public String getServerType() {
        return "BungeeCord";
    }

    private static BungeeMain instance;

    /**
     * Gets the singleton instance.
     * @return The singleton instance.
     */
    public static BungeeMain getInstance() {
        return instance;
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
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerCommand(this, new BungeeDiscordCommand());

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
