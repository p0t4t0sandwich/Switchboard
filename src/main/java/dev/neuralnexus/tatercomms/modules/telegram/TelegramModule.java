package dev.neuralnexus.tatercomms.modules.telegram;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.tatercomms.modules.telegram.api.TelegramServer;
import dev.neuralnexus.taterlib.plugin.PluginModule;

/** Telegram module. */
public class TelegramModule implements PluginModule {
    private static boolean STARTED = false;

    @Override
    public String name() {
        return "Telegram";
    }

    @Override
    public void start() {
        if (STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        // Check if the token and channel mappings are set
        String token = TaterCommsConfigLoader.config().telegram().token();
        if (token == null || token.isEmpty()) {
            TaterComms.logger().info("No Telegram token found in tatercomms.conf!");
            return;
        }
        if (TaterCommsConfigLoader.config().telegram().mappings().isEmpty()) {
            TaterComms.logger().info("No server-channel mappings found in tatercomms.conf!");
            return;
        }

        if (!TaterComms.hasReloaded()) {
            // Register events
            TaterCommsEvents.RECEIVE_MESSAGE.register(
                    (event) -> {
                        // Prevents telegram messages from being passed back to telegram
                        if (!(event.getMessage().getSender().server() instanceof TelegramServer)) {
                            TaterCommsAPIProvider.get()
                                    .telegramAPI()
                                    .sendMessage(event.getMessage());
                        }
                    });
        }

        // Start the bot
        TaterCommsAPIProvider.get().telegramAPI().startBot();
    }

    @Override
    public void stop() {
        if (!STARTED) {
            TaterComms.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        TaterCommsAPIProvider.get().telegramAPI().removeBot();
    }
}
