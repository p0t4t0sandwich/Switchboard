package dev.neuralnexus.tatercomms.common.modules.minecraft.listeners.server;

import dev.neuralnexus.tatercomms.common.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.common.api.message.Message;
import dev.neuralnexus.tatercomms.common.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.common.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.common.event.server.ServerStartedEvent;
import dev.neuralnexus.taterlib.common.event.server.ServerStoppedEvent;

import java.util.HashMap;

public interface TaterCommsServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStarted(ServerStartedEvent event) {
        String server = event.getServer().getName();
        if (server.equals("local")) {
            server = TaterCommsAPIProvider.get().getServerName();
        }
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                server,
                                Message.MessageType.SERVER_STARTED,
                                "**Server has started**",
                                TaterCommsAPIProvider.get().getFormatting().get("serverStarted"),
                                new HashMap<>())));
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStopped(ServerStoppedEvent event) {
        String server = event.getServer().getName();
        if (server.equals("local")) {
            server = TaterCommsAPIProvider.get().getServerName();
        }
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                server,
                                Message.MessageType.SERVER_STOPPED,
                                "**Server has stopped**",
                                TaterCommsAPIProvider.get().getFormatting().get("serverStopped"),
                                new HashMap<>())));
    }
}
