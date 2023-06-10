package ca.sperrer.p0t4t0sandwich.tatercomms.bungee;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.commands.BungeeTemplateCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.listeners.BungeePlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
    public TaterComms taterComms;

    // Get server type
    public String getServerType() {
        return "BungeeCord";
    }

    // Singleton instance
    private static BungeeMain instance;
    public static BungeeMain getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("TaterComms is running on " + getServerType() + ".");

        // Start
        taterComms = new TaterComms("plugins", getLogger());
        taterComms.start();

        // Register event listener
        getProxy().getPluginManager().registerListener(this, new BungeePlayerLoginListener());

        // Register commands
        getProxy().getPluginManager().registerCommand(this, new BungeeTemplateCommand());

        // Plugin enable message
        getLogger().info("TaterComms has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
