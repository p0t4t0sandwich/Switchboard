package dev.neuralnexus.switchboard.modules.minecraft.listeners.server;

import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterlib.event.server.ServerStartedEvent;
import dev.neuralnexus.taterlib.event.server.ServerStoppedEvent;

import java.util.HashMap;

public interface SwitchboardServerListener {
    /**
     * Called when a server starts, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStarted(ServerStartedEvent event) {
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                event.server(),
                                Message.MessageType.SERVER_STARTED,
                                "**Server has started**",
                                SwitchboardConfigLoader.config().formatting().serverStarted(),
                                new HashMap<>())));
    }

    /**
     * Called when a server stops, and sends it to the message relay.
     *
     * @param event The event
     */
    static void onServerStopped(ServerStoppedEvent event) {
        SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                new ReceiveMessageEvent(
                        new Message(
                                event.server(),
                                Message.MessageType.SERVER_STOPPED,
                                "**Server has stopped**",
                                SwitchboardConfigLoader.config().formatting().serverStopped(),
                                new HashMap<>())));
    }
}
