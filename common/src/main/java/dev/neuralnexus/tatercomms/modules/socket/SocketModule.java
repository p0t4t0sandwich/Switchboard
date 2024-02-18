package dev.neuralnexus.tatercomms.modules.socket;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** Socket module. */
public class SocketModule implements PluginModule {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String name() {
        return "Socket";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!RELOADED) {
            // Register events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    (event) -> TaterCommsAPIProvider.get().socketAPI().onReceiveMessage(event));
        }

        // Start the socket server
        TaterCommsAPIProvider.get().socketAPI().startSocket();

        TaterComms.logger().info("Submodule " + name() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects
        TaterCommsAPIProvider.get().socketAPI().stopSocket();

        TaterComms.logger().info("Submodule " + name() + " has been stopped!");
    }

    @Override
    public void reload() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        TaterComms.logger().info("Submodule " + name() + " has been reloaded!");
    }
}
