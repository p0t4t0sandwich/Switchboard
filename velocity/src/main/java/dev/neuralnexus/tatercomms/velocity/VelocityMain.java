package dev.neuralnexus.tatercomms.velocity;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.commands.DiscordCommand;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;
import dev.neuralnexus.tatercomms.velocity.commands.VelocityDiscordCommand;
import dev.neuralnexus.tatercomms.velocity.listeners.player.VelocityPlayerLoginListener;
import dev.neuralnexus.tatercomms.velocity.listeners.player.VelocityPlayerLogoutListener;
import dev.neuralnexus.tatercomms.velocity.listeners.player.VelocityPlayerMessageListener;
import dev.neuralnexus.tatercomms.velocity.listeners.player.VelocityPlayerServerSwitchListener;
import dev.neuralnexus.tatercomms.velocity.listeners.server.VelocityServerStartedListener;
import dev.neuralnexus.tatercomms.velocity.listeners.server.VelocityServerStoppedListener;
import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * The main class for the Velocity plugin.
 */
@Plugin(
        id = "tatercomms",
        name = "TaterComms",
        version = "1.0.0",
        authors = "p0t4t0sandwich",
        description = "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.",
        url = "https://github.com/p0t4t0sandwich/TaterComms",
        dependencies = {}
)
public class VelocityMain {
    public TaterComms taterComms;

    @Inject
    private ProxyServer server;

    @Inject
    private Logger logger;

    /**
     * Get the logger.
     * @return The logger
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Get the server type.
     * @return The server type
     */
    public String getServerType() {
        return "Velocity";
    }

    // Singleton instance
    private static VelocityMain instance;

    /**
     * Get the singleton instance.
     * @return The singleton instance
     */
    public static VelocityMain getInstance() {
        return instance;
    }

    /**
     * Get the proxy server.
     * @return The proxy server
     */
    public ProxyServer getServer() {
        return this.server;
    }

    /**
     * Called when the proxy is initialized.
     * @param event The proxy initialization event
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Singleton instance
        instance = this;

        this.logger.info("TaterComms is running on " + getServerType() + ".");

        // Start TaterComms
        taterComms = new TaterComms("plugins", getLogger());
        taterComms.start();

        // Server started listener
        (new VelocityServerStartedListener()).onServerStarted();

        // Register player event listeners
        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new VelocityPlayerLoginListener());
        eventManager.register(this, new VelocityPlayerLogoutListener());
        eventManager.register(this, new VelocityPlayerMessageListener());
        eventManager.register(this, new VelocityPlayerServerSwitchListener());

        // Register server event listeners
        eventManager.register(this, new VelocityServerStoppedListener());

        // Register commands
        server.getCommandManager().register(DiscordCommand.getCommandName(), new VelocityDiscordCommand());

        // Register LuckPerms hook
        if (getServer().getPluginManager().getPlugin("LuckPerms").isPresent()) {
            getLogger().info("LuckPerms detected, enabling LuckPerms hook.");
            TaterComms.addHook(new LuckPermsHook());
        }

        // Plugin enable message
        this.logger.info("TaterComms has been enabled!");
    }
}
