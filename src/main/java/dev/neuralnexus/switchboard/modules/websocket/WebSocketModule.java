/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.websocket;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** A module for WebSocket. */
public class WebSocketModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String name() {
        return "WebSocket";
    }

    @Override
    public void start() {
        if (STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        WebSocketConfig config = SwitchboardConfigLoader.config().webSocket();
        if (config.host() == null || config.host().isEmpty()) {
            Switchboard.logger().info("WebSocket host is not set, skipping start");
            return;
        }
        if (config.port() == 0) {
            Switchboard.logger().info("WebSocket port is not set, skipping start");
            return;
        }

        if (!Switchboard.hasReloaded()) {
            // Register events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    (event) ->
                            SwitchboardAPIProvider.get()
                                    .webSocketAPI()
                                    .sendMessage(event.getMessage()));
        }

        // Start the WebSocket server or client
        SwitchboardAPIProvider.get()
                .webSocketAPI()
                .startWebSocket(
                        config.host(),
                        config.port(),
                        config.secret(),
                        config.mode(),
                        TaterAPIProvider.get().getServer().name());
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        SwitchboardAPIProvider.get().webSocketAPI().stopWebSocket();
    }
}
