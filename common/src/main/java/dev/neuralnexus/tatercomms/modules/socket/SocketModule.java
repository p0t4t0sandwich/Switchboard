package dev.neuralnexus.tatercomms.modules.socket;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.plugin.Module;

/** Socket module. */
public class SocketModule implements Module {
    private static boolean STARTED = false;
    private static boolean RELOADED = false;

    @Override
    public String getName() {
        return "Socket";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already started!");
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

        TaterComms.getLogger().info("Submodule " + getName() + " has been started!");
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has already stopped!");
            return;
        }
        STARTED = false;
        RELOADED = true;

        // Remove references to objects
        TaterCommsAPIProvider.get().socketAPI().stopSocket();

        TaterComms.getLogger().info("Submodule " + getName() + " has been stopped!");
    }

    @Override
    public void reload() {
        if (!STARTED) {
            TaterComms.getLogger().info("Submodule " + getName() + " has not been started!");
            return;
        }
        RELOADED = true;

        // Stop
        stop();

        // Start
        start();

        TaterComms.getLogger().info("Submodule " + getName() + " has been reloaded!");
    }
}