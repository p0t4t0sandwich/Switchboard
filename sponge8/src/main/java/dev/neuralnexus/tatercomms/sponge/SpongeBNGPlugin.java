package dev.neuralnexus.tatercomms.sponge;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.tatercomms.sponge.commands.SpongeDiscordCommand;
import dev.neuralnexus.taterlib.bukkit.abstractions.player.BukkitPlayer;
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

import java.util.HashSet;
import java.util.Set;

@Plugin("tatercomms")
public class SpongeBNGPlugin extends TemplateSpongePlugin implements TaterCommsPlugin {
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
    public void registerEventListeners() {
        ChannelManager channelManager = Sponge.channelManager();
        Set<CommsMessage.MessageType> channels = CommsMessage.MessageType.getTypes();

        for (CommsMessage.MessageType channel : channels) {
            String identifier = channel.getIdentifier();
            String namespace = identifier.split(":")[0];
            String channelName = identifier.split(":")[1];
            channelManager.ofType(ResourceKey.of(namespace, channelName), RawDataChannel.class);
        }

        PlayerEvents.ADVANCEMENT_FINISHED.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String advancement = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterCommsConfig.serverName());
            CommsMessage commsMessage = new CommsMessage(commsSender, advancement);
            Player player = ((SpongePlayer) abstractPlayer).getPlayer();
            channelManager.ofType(ResourceKey.of("tatercomms", "player_advancement_finished"), RawDataChannel.class).play().sendTo((ServerPlayer) player, (buffer) -> buffer.writeBytes(commsMessage.toByteArray()));
        });

        PlayerEvents.DEATH.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String deathMessage = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterCommsConfig.serverName());
            CommsMessage commsMessage = new CommsMessage(commsSender, deathMessage);
            Player player = ((SpongePlayer) abstractPlayer).getPlayer();
            channelManager.ofType(ResourceKey.of("tatercomms", "player_death"), RawDataChannel.class).play().sendTo((ServerPlayer) player, (buffer) -> buffer.writeBytes(commsMessage.toByteArray()));
        });
    }

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
