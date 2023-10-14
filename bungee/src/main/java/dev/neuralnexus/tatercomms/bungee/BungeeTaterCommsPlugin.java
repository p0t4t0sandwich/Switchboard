package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.bungee.commands.BungeeDiscordCommand;
import dev.neuralnexus.tatercomms.bungee.messagelisteners.BungeeMessageListener;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bungee.TemplateBungeePlugin;

/**
 * The TaterComms Bungee plugin.
 */
public class BungeeTaterCommsPlugin extends TemplateBungeePlugin implements TaterCommsPlugin {

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new BungeeDiscordCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        getProxy().getPluginManager().registerListener(this, new BungeeMessageListener());
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
