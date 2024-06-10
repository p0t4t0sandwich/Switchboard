/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.origin;

import dev.neuralnexus.taterlib.server.SimpleServer;

public class MCOrigin implements Origin {
    private final SimpleServer server;

    public MCOrigin(SimpleServer server) {
        this.server = server;
    }

    @Override
    public String name() {
        return server.name();
    }

    @Override
    public void broadcast(String message) {
        server.broadcastMessage(message);
    }
}
