package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.bukkit.commands.BukkitDiscordCommand;
import dev.neuralnexus.tatercomms.bukkit.commands.BukkitTaterCommsCommand;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.tatercomms.common.commands.TaterCommsCommand;
import dev.neuralnexus.taterlib.bukkit.TemplateBukkitPlugin;

/**
 * The TaterComms Bukkit plugin.
 */
public class BukkitTaterCommsPlugin extends TemplateBukkitPlugin implements TaterCommsPlugin {
    @Override
    public void registerCommands() {
        getCommand(TaterCommsCommand.getCommandName()).setExecutor(new BukkitTaterCommsCommand());
        getCommand(DiscordCommand.getCommandName()).setExecutor(new BukkitDiscordCommand());
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
