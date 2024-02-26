package dev.neuralnexus.tatercomms.api.message;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.Collections;
import java.util.List;

/** Server for MessageSenders. */
public class MessageServer implements SimpleServer {
    private final String name;
    private final String brand;

    /** Constructor for the MessageServer class. */
    public MessageServer(SimpleServer server) {
        this.name = server.name();
        String brand;
        if (TaterAPIProvider.serverType().isVelocityBased()
                || TaterAPIProvider.serverType().isBungeeCordBased()) {
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
    public List<SimplePlayer> onlinePlayers() {
        return Collections.emptyList();
    }
}
