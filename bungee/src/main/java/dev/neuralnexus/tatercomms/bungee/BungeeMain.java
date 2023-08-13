package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.bungee.commands.BungeeDiscordCommand;
import dev.neuralnexus.tatercomms.bungee.listeners.player.BungeePlayerLoginListener;
import dev.neuralnexus.tatercomms.bungee.listeners.player.BungeePlayerLogoutListener;
import dev.neuralnexus.tatercomms.bungee.listeners.player.BungeePlayerMessageListener;
import dev.neuralnexus.tatercomms.bungee.listeners.player.BungeePlayerServerSwitchListener;
import dev.neuralnexus.tatercomms.bungee.listeners.server.BungeeServerStartedListener;
import dev.neuralnexus.tatercomms.bungee.listeners.server.BungeeServerStoppedListener;
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

        // Server started listener
        new BungeeServerStartedListener().onServerStarted();

        // Register event listener
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new BungeePlayerLoginListener());
        pluginManager.registerListener(this, new BungeePlayerLogoutListener());
        pluginManager.registerListener(this, new BungeePlayerMessageListener());
        pluginManager.registerListener(this, new BungeePlayerServerSwitchListener());

        // Register commands
        pluginManager.registerCommand(this, new BungeeDiscordCommand());

        // Register LuckPerms hook
        if (getProxy().getPluginManager().getPlugin("LuckPerms") != null) {
            getLogger().info("LuckPerms detected, enabling LuckPerms hook.");
            TaterComms.addHook(new LuckPermsHook());
        }

        // Plugin enable message
        getLogger().info("TaterComms has been enabled!");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        // Server stopped listener
        new BungeeServerStoppedListener().onServerStopped();

        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
