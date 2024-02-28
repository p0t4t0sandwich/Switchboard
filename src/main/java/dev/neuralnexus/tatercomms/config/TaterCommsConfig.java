package dev.neuralnexus.tatercomms.config;

import dev.neuralnexus.tatercomms.config.sections.discord.DiscordConfig;
import dev.neuralnexus.tatercomms.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.tatercomms.config.sections.socket.SocketConfig;
import dev.neuralnexus.tatercomms.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import java.util.Set;

/** A class for TaterComms configuration. */
public interface TaterCommsConfig {
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
    Set<ModuleConfig> modules();

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
     * Get the TCP Socket configuration.
     *
     * @return The TCP Socket configuration.
     */
    SocketConfig socket();

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
