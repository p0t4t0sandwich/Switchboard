/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.versions.SwitchboardConfig_V1;
import dev.neuralnexus.switchboard.config.versions.VersionedConfig;
import dev.neuralnexus.switchboard.logger.Logger;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/** A class for loading Switchboard configuration. */
public final class SwitchboardConfigLoader {
    private static final Logger logger = Logger.create(Switchboard.PROJECT_ID + "-configloader");
    private static Path configPath =
            Paths.get(".").toAbsolutePath().normalize().resolve(Switchboard.PROJECT_ID);
    private static HoconConfigurationLoader loader;
    private static SwitchboardConfig config;

    public static void setBasePath(Path path) {
        configPath = Path.of(path + File.separator + Switchboard.PROJECT_ID);
    }

    /** Load the configuration from the file. */
    public static void load() {
        loader =
                HoconConfigurationLoader.builder()
                        .path(configPath.resolve(Switchboard.PROJECT_ID + ".conf"))
                        .build();
        CommentedConfigurationNode node = null;
        try {
            node = loader.load();
        } catch (ConfigurateException e) {
            logger.error("An error occurred while loading the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
            }
        }
        if (node == null) {
            return;
        }

        int version = VersionedConfig.tryGetVersion(node, logger);
        switch (version) {
            case 1:
                try {
                    config = node.get(SwitchboardConfig_V1.class);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while loading the modules configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
                break;
            default:
                logger.error(
                        "Unknown configuration version: " + version + ", defaulting to version 1");
                config = new SwitchboardConfig_V1();
                try {
                    node.set(SwitchboardConfig_V1.class, config);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while updating the configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
        }

        try {
            loader.save(node);
        } catch (ConfigurateException e) {
            logger.error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
            }
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
        if (loader == null) {
            return;
        }
        CommentedConfigurationNode node = null;
        try {
            node = loader.load();
        } catch (ConfigurateException e) {
            logger.error("An error occurred while loading the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
            }
        }
        if (node == null) {
            return;
        }

        switch (config.version()) {
            case 1:
                try {
                    node.set(SwitchboardConfig_V1.class, config);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while updating the configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
                break;
            default:
                logger.error(
                        "Unknown configuration version: "
                                + config.version()
                                + ", defaulting to version 1");
                try {
                    node.set(SwitchboardConfig_V1.class, config);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while updating the configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
        }

        try {
            loader.save(node);
        } catch (ConfigurateException e) {
            logger.error("An error occurred while saving this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
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
