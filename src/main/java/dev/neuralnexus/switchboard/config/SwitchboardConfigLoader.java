/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.socket.SocketConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.switchboard.config.versions.SwitchboardConfig_V1;
import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.config.sections.ModuleConfig;

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
import java.util.Objects;
import java.util.Set;

/** A class for loading Switchboard configuration. */
public class SwitchboardConfigLoader {
    private static final Path configPath =
            Paths.get(
                    TaterAPIProvider.serverType().dataFolders().configFolder()
                            + File.separator
                            + Switchboard.Constants.PROJECT_NAME
                            + File.separator
                            + Switchboard.Constants.PROJECT_ID
                            + ".conf");
    private static final String defaultConfigPath =
            "source." + Switchboard.Constants.PROJECT_ID + ".conf";
    private static final TypeToken<Set<ModuleConfig>> moduleType =
            new TypeToken<Set<ModuleConfig>>() {};
    private static final TypeToken<DiscordConfig> discordType = new TypeToken<DiscordConfig>() {};
    private static final TypeToken<TelegramConfig> telegramType =
            new TypeToken<TelegramConfig>() {};
    private static final TypeToken<FormattingConfig> formattingType =
            new TypeToken<FormattingConfig>() {};
    private static final TypeToken<SocketConfig> socketType = new TypeToken<SocketConfig>() {};
    private static SwitchboardConfig config;

    /** Copy the default configuration to the config folder. */
    public static void copyDefaults() {
        if (configPath.toFile().exists()) {
            return;
        }
        try {
            Files.createDirectories(configPath.getParent());
            Files.copy(
                    Objects.requireNonNull(
                            SwitchboardConfigLoader.class
                                    .getClassLoader()
                                    .getResourceAsStream(defaultConfigPath)),
                    configPath);
        } catch (IOException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while copying the default configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }

    /** Load the configuration from the file. */
    public static void load() {
        copyDefaults();

        final HoconConfigurationLoader loader =
                HoconConfigurationLoader.builder().path(configPath).build();
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (ConfigurateException e) {
            Switchboard.logger()
                    .error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }

        ConfigurationNode versionNode = root.node("version");
        int version = versionNode.getInt(1);

        Set<ModuleConfig> modules = null;
        try {
            modules = root.node("modules").get(moduleType);
        } catch (SerializationException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while loading the modules configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        DiscordConfig discord = null;
        try {
            discord = root.node("discord").get(discordType);
        } catch (SerializationException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while loading the discord configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        TelegramConfig telegram = null;
        try {
            telegram = root.node("telegram").get(telegramType);
        } catch (SerializationException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while loading the telegram configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        FormattingConfig formatting = null;
        try {
            formatting = root.node("formatting").get(formattingType);
        } catch (SerializationException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while loading the formatting configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        SocketConfig socket = null;
        try {
            socket = root.node("socket").get(socketType);
        } catch (SerializationException e) {
            Switchboard.logger()
                    .error(
                            "An error occurred while loading the socket configuration: "
                                    + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        switch (version) {
            case 1:
                config =
                        new SwitchboardConfig_V1(
                                version, modules, discord, telegram, formatting, socket);
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
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (ConfigurateException e) {
            TaterLib.logger()
                    .error("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            return;
        }

        try {
            root.node("version").set(config.version());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("modules").set(moduleType, config.modules());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("discord").set(discordType, config.discord());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("telegram").set(telegramType, config.telegram());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("formatting").set(formattingType, config.formatting());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

        try {
            root.node("socket").set(socketType, config.socket());
        } catch (SerializationException e) {
            TaterLib.logger()
                    .error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }

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
