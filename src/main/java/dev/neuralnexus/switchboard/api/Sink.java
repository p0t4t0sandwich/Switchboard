/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

/** A destination for messages */
public interface Sink {
    /**
     * Get the name of the Sink
     *
     * @return The name of the Sink
     */
    String name();

    /**
     * Send a packet to the Sink
     *
     * @param packet The packet to send
     */
    void send(Packet packet);
}
