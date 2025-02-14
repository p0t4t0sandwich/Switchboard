/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import java.util.List;
import java.util.Map;

/** A Relay for routing messages between different Sources and Sinks */
public interface Relay {
    /**
     * Get the version of the Relay config
     *
     * @return The version of the Relay config
     */
    int version();

    /** Get the defined interfaces */
    List<Interface<?>> interfaces();

    /**
     * Get the defined channels
     *
     * @return The defined channels
     */
    Map<String, String> channels();

    // TODO: Processors
    // Processor processors();
}
