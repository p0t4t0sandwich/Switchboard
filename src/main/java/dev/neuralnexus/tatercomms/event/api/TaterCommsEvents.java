package dev.neuralnexus.tatercomms.event.api;

import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.taterlib.event.api.EventManager;

/**
 * TaterComms events.
 */
public class TaterCommsEvents {
    public static final EventManager<ReceiveMessageEvent> RECEIVE_MESSAGE = new EventManager<>(ReceiveMessageEvent.class);
}
