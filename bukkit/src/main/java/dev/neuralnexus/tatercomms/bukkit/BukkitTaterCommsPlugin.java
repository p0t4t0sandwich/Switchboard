package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.bukkit.commands.BukkitDiscordCommand;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsSender;
import dev.neuralnexus.taterlib.bukkit.BukkitTaterLibPlugin;
import dev.neuralnexus.taterlib.bukkit.TemplateBukkitPlugin;
import dev.neuralnexus.taterlib.bukkit.abstractions.player.BukkitPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
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
        CommsMessage.MessageType.getTypes().stream().map(CommsMessage.MessageType::getIdentifier).forEach((identifier) -> messenger.registerOutgoingPluginChannel(BukkitTaterLibPlugin.getInstance(), identifier));
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
