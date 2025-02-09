/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.message;

import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.entity.player.User;
import dev.neuralnexus.taterapi.server.SimpleServer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Server for MessageSenders. */
public class MessageServer implements SimpleServer {
    private final String name;
    private final String brand;

    /** Constructor for the MessageServer class. */
    public MessageServer(SimpleServer server) {
        this.name = server.name();
        String brand;
        if (MetaAPI.isProxy()) {
            brand = "Unknown";
        } else {
            brand = server.brand();
        }
        this.brand = brand;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String brand() {
        return brand;
    }

    @Override
    public List<User> onlinePlayers() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, UUID> playercache() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, UUID> whitelist() {
        return Collections.emptyMap();
    }
}
