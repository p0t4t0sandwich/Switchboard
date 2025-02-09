/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.event.api;

import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.taterapi.event.api.EventManager;

/** Switchboard events. */
public class SwitchboardEvents {
    public static final EventManager<ReceiveMessageEvent> RECEIVE_MESSAGE =
            new EventManager<>(ReceiveMessageEvent.class);
}
