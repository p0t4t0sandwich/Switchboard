package dev.neuralnexus.tatercomms.velocity.messagelisteners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.util.Arrays;

/**
 * Class for listening to plugin messages
 */
public class VelocityMessageListener {
    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!(event.getSource() instanceof ServerConnection)) {
            return;
        }
        CommsMessage.parseMessageChannel(event.getIdentifier().getId(), event.getData());
    }
}
