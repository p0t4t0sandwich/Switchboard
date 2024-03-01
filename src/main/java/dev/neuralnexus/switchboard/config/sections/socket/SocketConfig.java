/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.sections.socket;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.UUID;

/** A class for TCP socket configuration. */
@ConfigSerializable
public class SocketConfig {
    @Setting private boolean primary;
    @Setting private String host;
    @Setting private int port;
    @Setting private String secret;

    /**
     * Get whether the server is the primary server.
     *
     * @return Whether the server is the primary server
     */
    public boolean primary() {
        return primary;
    }

    /**
     * Get the remote host.
     *
     * @return The remote host
     */
    public String host() {
        return host;
    }

    /**
     * Get the remote port.
     *
     * @return The remote port
     */
    public int port() {
        return port;
    }

    /**
     * Get the remote secret.
     *
     * @return The remote secret
     */
    public String secret() {
        if (secret == null || secret.isEmpty()) {
            Switchboard.logger().info("Generating new remote secret");
            secret = UUID.randomUUID().toString();
            SwitchboardConfigLoader.save();
        }
        return secret;
    }
}
