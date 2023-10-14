package dev.neuralnexus.tatercomms.bungee.messagelisteners;

import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Class for listening to plugin messages
 */
public class BungeeMessageListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!(event.getReceiver() instanceof Server)) {
            return;
        }
        CommsMessage.parseMessageChannel(event.getTag(), event.getData());
    }
}
