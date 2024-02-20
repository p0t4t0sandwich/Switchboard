package dev.neuralnexus.tatercomms.modules.minecraft.listeners.server;

import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.event.server.ServerStartedEvent;
import dev.neuralnexus.taterlib.event.server.ServerStoppingEvent;

import java.util.HashMap;

public interface TaterCommsServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStarted(ServerStartedEvent event) {
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                event.server(),
                                Message.MessageType.SERVER_STARTED,
                                "**Server has started**",
                                TaterCommsConfigLoader.config().formatting().serverStarted(),
                                new HashMap<>())));
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStopped(ServerStoppingEvent event) {
        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                event.server(),
                                Message.MessageType.SERVER_STOPPED,
                                "**Server has stopped**",
                                TaterCommsConfigLoader.config().formatting().serverStopped(),
                                new HashMap<>())));
    }
}
