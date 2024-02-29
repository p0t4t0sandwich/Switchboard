package dev.neuralnexus.switchboard.modules.socket;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
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
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        if (!Switchboard.hasReloaded()) {
            // Register events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    (event) -> SwitchboardAPIProvider.get().socketAPI().onReceiveMessage(event));
        }

        // Start the socket server
        SwitchboardAPIProvider.get().socketAPI().startSocket();
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        SwitchboardAPIProvider.get().socketAPI().stopSocket();
    }
}
