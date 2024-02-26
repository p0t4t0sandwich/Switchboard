package dev.neuralnexus.tatercomms.modules.socket;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** Socket module. */
public class SocketModule implements PluginModule {
    private static boolean STARTED = false;

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

        if (!TaterComms.hasReloaded()) {
            // Register events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    (event) -> TaterCommsAPIProvider.get().socketAPI().onReceiveMessage(event));
        }

        // Start the socket server
        TaterCommsAPIProvider.get().socketAPI().startSocket();
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        TaterCommsAPIProvider.get().socketAPI().stopSocket();
    }
}
