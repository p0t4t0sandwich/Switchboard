package dev.neuralnexus.tatercomms.common.event.api;

import dev.neuralnexus.tatercomms.common.event.ReceiveMessageEvent;
import dev.neuralnexus.taterlib.common.event.api.Event;

/** TaterComms events. */
public class TaterCommsEvents {
    public static final Event<ReceiveMessageEvent> RECEIVE_MESSAGE =
            new Event<>(ReceiveMessageEvent.class);
}
