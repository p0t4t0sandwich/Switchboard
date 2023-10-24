package dev.neuralnexus.tatercomms.common.listeners.server;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.taterlib.common.TaterLib;

import java.util.HashMap;

public interface CommonServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     */
    static void onServerStarted(Object[] args) {
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        String server;
        if (args.length > 0) {
            server = args[0].toString();
        } else {
            server = TaterCommsConfig.serverName();
        }
        relay.relayMessage(new CommsMessage(server,
                CommsMessage.MessageType.SERVER_STARTED,
                "**Server has started**",
                TaterCommsConfig.formattingChat().get("serverStarted"),
                new HashMap<>()
        ));
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     */
    static void onServerStopped(Object[] args) {
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        String server;
        if (args.length > 0) {
            server = args[0].toString();
        } else {
            server = TaterCommsConfig.serverName();
        }
        relay.relayMessage(new CommsMessage(server,
                CommsMessage.MessageType.SERVER_STOPPED,
                "**Server has stopped**",
                TaterCommsConfig.formattingChat().get("serverStopped"),
                new HashMap<>()
        ));
    }
}
