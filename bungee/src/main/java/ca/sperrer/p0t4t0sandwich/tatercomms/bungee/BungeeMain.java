package ca.sperrer.p0t4t0sandwich.tatercomms.bungee;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.commands.BungeeTemplateCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player.BungeePlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player.BungeePlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player.BungeePlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.player.BungeePlayerServerSwitchListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.server.BungeeServerStartedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.server.BungeeServerStoppedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import net.md_5.bungee.api.plugin.Plugin;

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
        (new BungeeServerStartedListener()).onServerStarted();

        // Register event listener
        getProxy().getPluginManager().registerListener(this, new BungeePlayerLoginListener());
        getProxy().getPluginManager().registerListener(this, new BungeePlayerLogoutListener());
        getProxy().getPluginManager().registerListener(this, new BungeePlayerMessageListener());
        getProxy().getPluginManager().registerListener(this, new BungeePlayerServerSwitchListener());

        // Register commands
        getProxy().getPluginManager().registerCommand(this, new BungeeTemplateCommand());

        // Plugin enable message
        getLogger().info("TaterComms has been enabled!");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        // Server stopped listener
        (new BungeeServerStoppedListener()).onServerStopped();

        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
