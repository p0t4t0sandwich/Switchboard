package ca.sperrer.p0t4t0sandwich.tatercomms.bukkit;

import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.commands.BukkitTemplateCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.BukkitPlayerAdvancementListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.BukkitPlayerLoginListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.BukkitPlayerLogoutListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.bukkit.listeners.BukkitPlayerMessageListener;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import org.bukkit.plugin.java.JavaPlugin;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.*;

public class BukkitMain extends JavaPlugin {
    public TaterComms taterComms;

    // Singleton instance
    private static BukkitMain instance;
    public static BukkitMain getInstance() {
        return instance;
    }

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

    @Override
    public void onEnable() {
        // Singleton instance
        instance = this;

        getLogger().info("Template is running on " + getServerType() + ".");

        // Start
        taterComms = new TaterComms("plugins", getLogger());
        taterComms.start();

        // Register event listener
        getServer().getPluginManager().registerEvents(new BukkitPlayerAdvancementListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerLoginListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerLogoutListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitPlayerMessageListener(), this);

        // Register commands
        getCommand("template").setExecutor(new BukkitTemplateCommand());

        // Plugin enable message
        getLogger().info("Template has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("Template has been disabled!");
    }
}
