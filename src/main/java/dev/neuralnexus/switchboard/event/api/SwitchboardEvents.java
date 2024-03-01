/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.event.api;

import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.taterlib.event.api.EventManager;

/** Switchboard events. */
public class SwitchboardEvents {
    public static final EventManager<ReceiveMessageEvent> RECEIVE_MESSAGE =
            new EventManager<>(ReceiveMessageEvent.class);
}
