/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework;

public class Packet {
    private final int version;
    private final String origin;
    private final String dest;
    private final String sender;
    private final String message_id;
    private final String message_type;
    private final boolean encrypted;
    private final String enc_scheme;
    private final String content;

    public Packet(
            int version,
            String origin,
            String dest,
            String sender,
            String message_id,
            String message_type,
            boolean encrypted,
            String enc_scheme,
            String content) {
        this.version = version;
        this.origin = origin;
        this.dest = dest;
        this.sender = sender;
        this.message_id = message_id;
        this.message_type = message_type;
        this.encrypted = encrypted;
        this.enc_scheme = enc_scheme;
        this.content = content;
    }

    public int version() {
        return this.version;
    }

    public String origin() {
        return this.origin;
    }

    public String dest() {
        return this.dest;
    }

    public String sender() {
        return this.sender;
    }

    public String message_id() {
        return this.message_id;
    }

    public String message_type() {
        return this.message_type;
    }

    public boolean encrypted() {
        return this.encrypted;
    }

    public String enc_scheme() {
        return this.enc_scheme;
    }

    public String content() {
        return this.content;
    }
}
