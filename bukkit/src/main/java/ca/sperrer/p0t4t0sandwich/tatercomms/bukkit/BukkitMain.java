package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.commands.BukkitTemplateCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player.BukkitPlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player.BukkitPlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player.BukkitPlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.player.BukkitPlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server.BukkitServerStartedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.server.BukkitServerStoppedListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
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
        getServer().getPluginManager().registerEvents(new BukkitPlayerAdvancementListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerLoginListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerLogoutListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerMessageListener(), this);

        // Register server event listeners
        getServer().getPluginManager().registerEvents(new BukkitServerStartedListener(), this);

        // Register commands
        getCommand("template").setExecutor(new BukkitTemplateCommand());

        // Plugin enable message
        getLogger().info("TaterComms has been enabled!");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        // Server stopped listener
        (new BukkitServerStoppedListener()).onServerStopped();

        // Plugin disable message
        getLogger().info("TaterComms has been disabled!");
    }
}
