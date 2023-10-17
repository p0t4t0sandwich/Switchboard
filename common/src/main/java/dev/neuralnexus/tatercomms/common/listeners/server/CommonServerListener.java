package dev.neuralnexus.tatercomms.common.listeners.server;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.relay.MessageRelay;

public interface CommonServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     */
    static void onServerStarted(Object[] args) {
        MessageRelay relay = TaterLib.getMessageRelay();
        String server;
        if (args.length > 0) {
            server = args[0].toString();
        } else {
            server = TaterCommsConfig.serverName();
        }
        relay.sendSystemMessage(server, "**Server has started**");
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     */
    static void onServerStopped(Object[] args) {
        MessageRelay relay = TaterLib.getMessageRelay();
        String server;
        if (args.length > 0) {
            server = args[0].toString();
        } else {
            server = TaterCommsConfig.serverName();
        }
        relay.sendSystemMessage(server, "**Server has stopped**");
    }
}
