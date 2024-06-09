/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import java.util.List;

/** A class for Switchboard configuration. */
public interface SwitchboardConfig {
    /**
     * Get the version of the configuration.
     *
     * @return The version of the configuration.
     */
    int version();

    /**
     * Get the modules in the configuration.
     *
     * @return The modules in the configuration.
     */
    List<ModuleConfig> modules();

    /**
     * Get the Discord configuration.
     *
     * @return The Discord configuration.
     */
    DiscordConfig discord();

    /**
     * Get the Telegram configuration.
     *
     * @return The Telegram configuration.
     */
    TelegramConfig telegram();

    /**
     * Get the WebSocket configuration.
     *
     * @return The WebSocket configuration.
     */
    WebSocketConfig webSocket();

    /**
     * Get the message formatting configuration.
     *
     * @return The message formatting configuration.
     */
    FormattingConfig formatting();

    /**
     * Check if a module is enabled in the configuration.
     *
     * @param moduleName The name of the module.
     * @return Whether the module should be applied.
     */
    default boolean checkModule(String moduleName) {
        return modules().stream()
                .anyMatch(
                        moduleConfig ->
                                moduleConfig.name().equalsIgnoreCase(moduleName)
                                        && moduleConfig.enabled());
    }
}
