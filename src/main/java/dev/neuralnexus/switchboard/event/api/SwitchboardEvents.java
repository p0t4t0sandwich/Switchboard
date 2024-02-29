package dev.neuralnexus.switchboard.event.api;

import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.taterlib.event.api.EventManager;

/** Switchboard events. */
public class SwitchboardEvents {
    public static final EventManager<ReceiveMessageEvent> RECEIVE_MESSAGE =
            new EventManager<>(ReceiveMessageEvent.class);
}
