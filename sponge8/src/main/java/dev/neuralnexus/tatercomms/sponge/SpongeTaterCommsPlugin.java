package dev.neuralnexus.tatercomms.sponge;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.tatercomms.sponge.commands.SpongeDiscordCommand;
import dev.neuralnexus.taterlib.common.abstractions.logger.AbstractLogger;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
import dev.neuralnexus.taterlib.sponge.TemplateSpongePlugin;
import dev.neuralnexus.taterlib.sponge.abstractions.logger.SpongeLogger;
import dev.neuralnexus.taterlib.sponge.abstractions.player.SpongePlayer;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.network.channel.ChannelManager;
import org.spongepowered.api.network.channel.raw.RawDataChannel;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.util.Set;

@Plugin("tatercomms")
public class SpongeTaterCommsPlugin extends TemplateSpongePlugin implements TaterCommsPlugin {
    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    /**
     * @inheritDoc
     */
    @Override
    public AbstractLogger pluginLogger() {
        return new SpongeLogger(logger);
    }

    /**
     * Register commands.
     * @param event The event
     */
    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        new SpongeDiscordCommand().onRegisterCommands(container, event);
    }

    @Override
    public void registerEventListeners() {}

    /**
     * Fired when the server starts.
     * @param event The event
     */
    @Listener
    public void onServerStarting(StartingEngineEvent<Server> event) {
        pluginStart();
    }

    /**
     * Fired when the server stops.
     * @param event The event
     */
    @Listener
    public void onServerStop(StoppingEngineEvent<Server> event) {
        pluginStop();
    }
}
