package dev.neuralnexus.tatercomms.bukkit;

import dev.neuralnexus.tatercomms.bukkit.commands.BukkitDiscordCommand;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.tatercomms.common.commands.DiscordCommand;
import dev.neuralnexus.taterlib.bukkit.TemplateBukkitPlugin;

/**
 * The TaterComms Bukkit plugin.
 */
public class BukkitTaterCommsPlugin extends TemplateBukkitPlugin implements TaterCommsPlugin {
    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        getCommand(DiscordCommand.getCommandName()).setExecutor(new BukkitDiscordCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        pluginStart();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        pluginStop();
    }
}
