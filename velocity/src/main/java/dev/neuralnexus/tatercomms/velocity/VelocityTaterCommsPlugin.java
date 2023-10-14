package dev.neuralnexus.tatercomms.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelRegistrar;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.velocity.commands.VelocityDiscordCommand;
import dev.neuralnexus.tatercomms.velocity.messagelisteners.VelocityMessageListener;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.velocity.TemplateVelocityPlugin;
import dev.neuralnexus.taterlib.velocity.abstractions.logger.VelocityLogger;
import org.slf4j.Logger;

/**
 * The TaterComms Velocity plugin.
 */
@Plugin(
        id = "tatercomms",
        name = "TaterComms",
        version = "1.0.3",
        authors = "p0t4t0sandwich",
        description = "A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.",
        url = "https://github.com/p0t4t0sandwich/TaterComms",
        dependencies = {
                @Dependency(id = "taterlib"),
                @Dependency(id = "luckperms", optional = true)
        }
)
public class VelocityTaterCommsPlugin extends TemplateVelocityPlugin implements TaterCommsPlugin {
    @Inject private ProxyServer server;
    @Inject private Logger logger;

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new VelocityLogger(logger);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        server.getCommandManager().register(DiscordCommand.getCommandName(), new VelocityDiscordCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        // Register channels
        ChannelRegistrar channelRegistrar = server.getChannelRegistrar();
        channelRegistrar.register(MinecraftChannelIdentifier.from(CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier()));
        channelRegistrar.register(MinecraftChannelIdentifier.from(CommsMessage.MessageType.PLAYER_DEATH.getIdentifier()));
        channelRegistrar.register(MinecraftChannelIdentifier.from(CommsMessage.MessageType.PLAYER_LOGIN.getIdentifier()));
        channelRegistrar.register(MinecraftChannelIdentifier.from(CommsMessage.MessageType.PLAYER_LOGOUT.getIdentifier()));

        // Register channel listener
        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new VelocityMessageListener());
    }

    /**
     * Called when the proxy is initialized.
     * @param event The event.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxyServer = server;
        pluginStart();
    }

    /**
     * Called when the proxy is shutting down.
     * @param event The event.
     */
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        pluginStop();
    }
}
