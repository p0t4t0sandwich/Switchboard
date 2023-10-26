package dev.neuralnexus.tatercomms.bungee;

import dev.neuralnexus.tatercomms.bungee.commands.BungeeDiscordCommand;
import dev.neuralnexus.tatercomms.bungee.commands.BungeeTaterCommsCommand;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsPlugin;
import dev.neuralnexus.taterlib.bungee.TemplateBungeePlugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * The TaterComms Bungee plugin.
 */
public class BungeeTaterCommsPlugin extends TemplateBungeePlugin implements TaterCommsPlugin {

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerCommand(this, new BungeeTaterCommsCommand());
        pluginManager.registerCommand(this, new BungeeDiscordCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        TaterComms.setProxyServers(() -> getProxy().getServers().keySet());
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
