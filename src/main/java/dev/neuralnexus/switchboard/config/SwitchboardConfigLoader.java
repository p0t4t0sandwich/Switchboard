/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;
import dev.neuralnexus.switchboard.config.versions.SwitchboardConfig_V1;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.logger.Logger;
import dev.neuralnexus.taterapi.util.ConfigUtil;
import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;
import dev.neuralnexus.taterloader.Loader;

import io.leangen.geantyref.TypeToken;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/** A class for loading Switchboard configuration. */
public class SwitchboardConfigLoader {
    private static final Logger logger =
            Loader.instance().logger(Switchboard.PROJECT_ID + "-configloader");
    private static final Path configPath =
            Paths.get(
                    TaterAPIProvider.platformData().configFolder()
                            + File.separator
                            + Switchboard.PROJECT_ID
                            + File.separator
                            + Switchboard.PROJECT_ID
                            + ".conf");
    private static final String defaultConfigPath = "source." + Switchboard.PROJECT_ID + ".conf";
    private static final TypeToken<Integer> versionType = new TypeToken<Integer>() {};
    private static final TypeToken<List<ModuleConfig>> moduleType =
            new TypeToken<List<ModuleConfig>>() {};
    private static final TypeToken<DiscordConfig> discordType = new TypeToken<DiscordConfig>() {};
    private static final TypeToken<TelegramConfig> telegramType =
            new TypeToken<TelegramConfig>() {};
    private static final TypeToken<FormattingConfig> formattingType =
            new TypeToken<FormattingConfig>() {};
    private static final TypeToken<WebSocketConfig> webSocketType =
            new TypeToken<WebSocketConfig>() {};
    private static SwitchboardConfig config;

    /** Load the configuration from the file. */
    public static void load() {
        ConfigUtil.copyDefaults(Switchboard.class, configPath, defaultConfigPath, logger);

        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root = ConfigUtil.getRoot(loader, logger);
        if (root == null) {
            return;
        }

        ConfigurationNode versionNode = root.node("version");
        int version = versionNode.getInt(1);

        List<ModuleConfig> modules = ConfigUtil.get(root, moduleType, "modules", logger);
        DiscordConfig discord = ConfigUtil.get(root, discordType, "discord", logger);
        TelegramConfig telegram = ConfigUtil.get(root, telegramType, "telegram", logger);
        FormattingConfig formatting = ConfigUtil.get(root, formattingType, "formatting", logger);
        WebSocketConfig webSocket = ConfigUtil.get(root, webSocketType, "websocket", logger);

        switch (version) {
            case 1:
                config =
                        new SwitchboardConfig_V1(
                                version, modules, discord, telegram, formatting, webSocket);
                break;
            default:
                logger.error("Unknown configuration version: " + version);
        }
    }

    /** Unload the configuration. */
    public static void unload() {
        config = null;
    }

    /** Save the configuration to the file. */
    public static void save() {
        if (config == null) {
            return;
        }
        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root = ConfigUtil.getRoot(loader, logger);
        if (root == null) {
            return;
        }

        ConfigUtil.set(root, versionType, "version", config.version(), logger);
        ConfigUtil.set(root, moduleType, "modules", config.modules(), logger);
        ConfigUtil.set(root, discordType, "discord", config.discord(), logger);
        ConfigUtil.set(root, telegramType, "telegram", config.telegram(), logger);
        ConfigUtil.set(root, formattingType, "formatting", config.formatting(), logger);
        ConfigUtil.set(root, webSocketType, "websocket", config.webSocket(), logger);

        try {
            loader.save(root);
        } catch (ConfigurateException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /**
     * Get the loaded configuration.
     *
     * @return The loaded configuration.
     */
    public static SwitchboardConfig config() {
        if (config == null) {
            load();
        }
        return config;
    }
}
