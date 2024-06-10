/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.message;

import dev.neuralnexus.switchboard.api.rework.Packet;
import dev.neuralnexus.switchboard.api.rework.origin.Origin;
import dev.neuralnexus.switchboard.api.rework.sender.Sender;

import java.util.UUID;

/** Message abstraction */
public interface Message {
    default int version() {
        return 1;
    }

    Origin origin();

    Sender sender();

    UUID message_id();

    String message_type();

    default boolean encrypted() {
        return false;
    }

    default String enc_scheme() {
        return "";
    }

    String content();

    default Packet toPacket(String dest) {
        return new Packet(
                version(),
                origin().name(),
                dest,
                sender().name(),
                message_id().toString(),
                message_type(),
                encrypted(),
                enc_scheme(),
                content());
    }
}
