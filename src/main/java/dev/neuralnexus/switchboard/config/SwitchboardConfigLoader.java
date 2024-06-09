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
import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

import dev.neuralnexus.taterlib.logger.AbstractLogger;
import io.leangen.geantyref.TypeToken;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/** A class for loading Switchboard configuration. */
public class SwitchboardConfigLoader {
    private static final Path configPath =
            Paths.get(
                    TaterAPIProvider.serverType().dataFolders().configFolder()
                            + File.separator
                            + Switchboard.PROJECT_NAME
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

    // TODO: REMOVE WHEN TATERLIB VERSION IS BUMPED
    public static CommentedConfigurationNode getRoot(HoconConfigurationLoader loader) {
        try {
            return loader.load();
        } catch (ConfigurateException e) {
            TaterLib.logger()
                    .error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return null;
        }
    }

    // TODO: REMOVE WHEN TATERLIB VERSION IS BUMPED
    public static <T> T get(
            CommentedConfigurationNode root,
            TypeToken<T> typeToken,
            String path,
            AbstractLogger logger) {
        try {
            return root.node(path).get(typeToken);
        } catch (SerializationException e) {
            logger.error(
                    "An error occurred while loading the modules configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return null;
        }
    }

    // TODO: REMOVE WHEN TATERLIB VERSION IS BUMPED
    public static <T> void set(
            CommentedConfigurationNode root,
            TypeToken<T> typeToken,
            String path,
            T value,
            AbstractLogger logger) {
        try {
            root.node(path).set(typeToken, value);
        } catch (SerializationException e) {
            logger.error(
                    "An error occurred while saving the modules configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    // TODO: REMOVE WHEN TATERLIB VERSION IS BUMPED
    public static <T> void copyDefaults(
            Class<T> clazz, Path configPath, String defaultConfigPath, AbstractLogger logger) {
        if (configPath.toFile().exists()) {
            return;
        }
        try {
            Files.createDirectories(configPath.getParent());
            Files.copy(
                    Objects.requireNonNull(
                            clazz.getClassLoader().getResourceAsStream(defaultConfigPath)),
                    configPath);
        } catch (IOException e) {
            logger.error(
                    "An error occurred while copying the default configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /** Load the configuration from the file. */
    public static void load() {
        copyDefaults(Switchboard.class, configPath, defaultConfigPath, Switchboard.logger());

        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root = getRoot(loader);
        if (root == null) {
            return;
        }

        ConfigurationNode versionNode = root.node("version");
        int version = versionNode.getInt(1);

        List<ModuleConfig> modules = get(root, moduleType, "modules", Switchboard.logger());
        DiscordConfig discord = get(root, discordType, "discord", Switchboard.logger());
        TelegramConfig telegram = get(root, telegramType, "telegram", Switchboard.logger());
        FormattingConfig formatting = get(root, formattingType, "formatting", Switchboard.logger());
        WebSocketConfig webSocket = get(root, webSocketType, "websocket", Switchboard.logger());

        switch (version) {
            case 1:
                config =
                        new SwitchboardConfig_V1(
                                version, modules, discord, telegram, formatting, webSocket);
                break;
            default:
                Switchboard.logger().error("Unknown configuration version: " + version);
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
        CommentedConfigurationNode root = getRoot(loader);
        if (root == null) {
            return;
        }

        set(root, versionType, "version", config.version(), Switchboard.logger());
        set(root, moduleType, "modules", config.modules(), Switchboard.logger());
        set(root, discordType, "discord", config.discord(), Switchboard.logger());
        set(root, telegramType, "telegram", config.telegram(), Switchboard.logger());
        set(root, formattingType, "formatting", config.formatting(), Switchboard.logger());
        set(root, webSocketType, "websocket", config.webSocket(), Switchboard.logger());

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
