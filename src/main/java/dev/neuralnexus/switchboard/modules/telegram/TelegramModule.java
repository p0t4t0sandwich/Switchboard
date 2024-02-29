package dev.neuralnexus.switchboard.modules.telegram;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.SwitchboardAPIProvider;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.switchboard.modules.telegram.api.TelegramServer;
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
            Switchboard.logger().info("Submodule " + name() + " has already started!");
            return;
        }
        STARTED = true;

        // Check if the token and channel mappings are set
        String token = SwitchboardConfigLoader.config().telegram().token();
        if (token == null || token.isEmpty()) {
            Switchboard.logger().info("No Telegram token found in switchboard.conf!");
            return;
        }
        if (SwitchboardConfigLoader.config().telegram().mappings().isEmpty()) {
            Switchboard.logger().info("No server-channel mappings found in switchboard.conf!");
            return;
        }

        if (!Switchboard.hasReloaded()) {
            // Register events
            SwitchboardEvents.RECEIVE_MESSAGE.register(
                    (event) -> {
                        // Prevents telegram messages from being passed back to telegram
                        if (!(event.getMessage().sender().server() instanceof TelegramServer)) {
                            SwitchboardAPIProvider.get()
                                    .telegramAPI()
                                    .sendMessage(event.getMessage());
                        }
                    });
        }

        // Start the bot
        SwitchboardAPIProvider.get().telegramAPI().startBot();
    }

    @Override
    public void stop() {
        if (!STARTED) {
            Switchboard.logger().info("Submodule " + name() + " has already stopped!");
            return;
        }
        STARTED = false;

        // Remove references to objects
        SwitchboardAPIProvider.get().telegramAPI().removeBot();
    }
}
