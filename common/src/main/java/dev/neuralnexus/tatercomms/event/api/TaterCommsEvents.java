package dev.neuralnexus.tatercomms.event.api;

import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.taterlib.event.api.Event;

/** TaterComms events. */
public class TaterCommsEvents {
    public static final Event<ReceiveMessageEvent> RECEIVE_MESSAGE =
            new Event<>(ReceiveMessageEvent.class);
}
