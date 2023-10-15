package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.bukkit.commands.BukkitDiscordCommand;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.taterlib.bukkit.TemplateBukkitPlugin;
import dev.neuralnexus.taterlib.bukkit.abstractions.player.BukkitPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

/**
 * The TaterComms Bukkit plugin.
 */
public class BukkitTaterCommsPlugin extends TemplateBukkitPlugin implements TaterCommsPlugin {
    @Override
    public void registerCommands() {
        getCommand(DiscordCommand.getCommandName()).setExecutor(new BukkitDiscordCommand());
    }

    @Override
    public void registerEventListeners() {
        Messenger messenger = getServer().getMessenger();
//        messenger.registerOutgoingPluginChannel(this, CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier());
//        messenger.registerOutgoingPluginChannel(this, CommsMessage.MessageType.PLAYER_DEATH.getIdentifier());
        CommsMessage.MessageType.getTypes().stream().map(CommsMessage.MessageType::getIdentifier).forEach((identifier) -> messenger.registerOutgoingPluginChannel(this, identifier));


        PlayerEvents.ADVANCEMENT_FINISHED.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String advancement = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterComms.getServerName());
            CommsMessage commsMessage = new CommsMessage(commsSender, advancement);
            Player player = ((BukkitPlayer) abstractPlayer).getPlayer();
            player.sendPluginMessage(this, CommsMessage.MessageType.PLAYER_ADVANCEMENT_FINISHED.getIdentifier(), commsMessage.toByteArray());
        });

        PlayerEvents.DEATH.register((args) -> {
            AbstractPlayer abstractPlayer = (AbstractPlayer) args[0];
            String deathMessage = (String) args[1];
            CommsSender commsSender = new CommsSender(abstractPlayer, TaterComms.getServerName());
            CommsMessage commsMessage = new CommsMessage(commsSender, deathMessage);
            Player player = ((BukkitPlayer) abstractPlayer).getPlayer();
            player.sendPluginMessage(this, CommsMessage.MessageType.PLAYER_DEATH.getIdentifier(), commsMessage.toByteArray());
        });
    }

    @Override
    public void onEnable() {
        pluginStart();
    }

    @Override
    public void onDisable() {
        pluginStop();
    }
}
