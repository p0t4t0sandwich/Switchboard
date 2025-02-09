/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import java.util.UUID;

/**
 * Message abstraction
 */
public record Message(String source, String sender, String type, String content) {
    public int version() {
        return 1;
    }

    public Packet toPacket(String sink) {
        return new Packet(
                this.version(),
                this.source(),
                sink,
                this.sender(),
                UUID.randomUUID(),
                this.type(),
                this.content());
    }
}
