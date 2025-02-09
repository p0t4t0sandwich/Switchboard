/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.webhook;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterapi.loader.plugin.PluginModule;

/** Webhook module. */
public class WebhookModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String id() {
        return "Webhook";
    }

    @Override
    public void onEnable() {
        if (STARTED) {
            Switchboard.logger().info("Submodule " + id() + " has already started!");
            return;
        }
        STARTED = true;

        if (!Switchboard.hasReloaded()) {
            // Register events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    event ->
                            SwitchboardAPIProvider.get()
                                    .webhookAPI()
                                    .sendMessage(event.getMessage()));
        }
    }

    @Override
    public void onDisable() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + id() + " has already stopped!");
            return;
        }
        STARTED = false;
    }
}
