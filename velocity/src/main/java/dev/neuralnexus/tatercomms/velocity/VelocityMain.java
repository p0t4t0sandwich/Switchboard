package dev.neuralnexus.tatercomms.velocity;

import com.velocitypowered.api.plugin.Dependency;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.velocity.commands.VelocityDiscordCommand;
import com.google.inject.Inject;
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
        dependencies = {
                @Dependency(id = "taterlib"),
                @Dependency(id = "luckperms", optional = true)
        }
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

        // Register commands
        server.getCommandManager().register(DiscordCommand.getCommandName(), new VelocityDiscordCommand());

        // Plugin enable message
        this.logger.info("TaterComms has been enabled!");
    }
}
