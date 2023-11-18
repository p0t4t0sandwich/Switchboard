package dev.neuralnexus.tatercomms.common.listeners.server;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.event.server.ServerStartedEvent;
import dev.neuralnexus.taterlib.common.event.server.ServerStoppedEvent;

import java.util.HashMap;
import java.util.Objects;

public interface CommonServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     * @param event The event
     */
    static void onServerStarted(ServerStartedEvent event) {
        String server = event.getServer().getName();
        if (server.equals("local")) {
            server = TaterCommsConfig.serverName();
        }
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(server,
                CommsMessage.MessageType.SERVER_STARTED,
                "**Server has started**",
                TaterCommsConfig.formattingChat().get("serverStarted"),
                new HashMap<>()
        ));
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     * @param event The event
     */
    static void onServerStopped(ServerStoppedEvent event) {
        String server = event.getServer().getName();
        if (server.equals("local")) {
            server = TaterCommsConfig.serverName();
        }
        TaterComms.getMessageRelay().relayMessage(new CommsMessage(server,
                CommsMessage.MessageType.SERVER_STOPPED,
                "**Server has stopped**",
                TaterCommsConfig.formattingChat().get("serverStopped"),
                new HashMap<>()
        ));
    }
}
