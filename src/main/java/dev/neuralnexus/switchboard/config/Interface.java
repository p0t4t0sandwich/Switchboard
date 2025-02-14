/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

/** Defines the config for a source or a sink */
public interface Interface<T> {
    /**
     * Get the name of the interface
     *
     * @return The name of the interface
     */
    String name();

    /**
     * Get the type of the interface
     *
     * @return The type of the interface
     */
    String type();

    /** Get the node's mode */
    Mode mode();

    /**
     * Get the configuration of the interface
     *
     * @return The configuration of the interface
     */
    T config();

    enum Mode {
        BOTH("both"),
        SOURCE("source"),
        SINK("sink");

        private final String mode;

        Mode(String mode) {
            this.mode = mode;
        }
    }
}
