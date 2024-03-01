/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.sections.websocket;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.modules.websocket.api.EncryptionHandler;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** A class for WebSocket configuration. */
@ConfigSerializable
public class WebSocketConfig {
    @Setting private String mode;
    @Setting private String host;
    @Setting private int port;
    @Setting private String secret;

    /**
     * Get the mode.
     *
     * @return The mode
     */
    public String mode() {
        return mode;
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
            secret = EncryptionHandler.generateKey();
            SwitchboardConfigLoader.save();
        }
        return secret;
    }
}
