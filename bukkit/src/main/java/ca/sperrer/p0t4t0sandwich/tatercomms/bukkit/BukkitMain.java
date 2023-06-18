package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player.*;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server.BukkitServerStartedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server.BukkitServerStoppedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.*;

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
        if (isMagma()) {
            return "Magma";
        } else if (isMohist()) {
            return "Mohist";
        } else if (isArclight()) {
            return "Arclight";
        } else if (isFolia()) {
            return "Folia";
        } else if (isPaper()) {
            return "Paper";
        } else if (isSpigot()) {
            return "Spigot";
        } else if (isCraftBukkit()) {
            return "CraftBukkit";
        } else {
            return "Unknown";
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("Template is running on " + getServerType() + ".");

        // Start
        taterComms = new TaterComms("plugins", getLogger());
        taterComms.start();

        // Register player event listeners
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BukkitPlayerAdvancementListener(), this);
        pluginManager.registerEvents(new BukkitPlayerDeathListener(), this);
        pluginManager.registerEvents(new BukkitPlayerLoginListener(), this);
        pluginManager.registerEvents(new BukkitPlayerLogoutListener(), this);
        pluginManager.registerEvents(new BukkitPlayerMessageListener(), this);

        // Register server event listeners
        runTaskLaterAsync(() -> new BukkitServerStartedListener().onServerStarted(), 100L);

        // Register commands
//        getCommand("template").setExecutor(new BukkitTemplateCommand());

        // Register LuckPerms hook
        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
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
        new BukkitServerStoppedListener().onServerStopped();

        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
