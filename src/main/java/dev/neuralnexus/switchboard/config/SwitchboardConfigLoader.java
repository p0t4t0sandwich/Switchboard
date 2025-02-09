/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.modapi.metadata.Logger;
import dev.neuralnexus.modapi.metadata.MetaAPI;
import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.switchboard.config.versions.SwitchboardConfig_V1;
import dev.neuralnexus.taterapi.config.VersionedConfig;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/** A class for loading Switchboard configuration. */
public class SwitchboardConfigLoader {
    private static final Logger logger = Logger.create(SwitchboardPlugin.PROJECT_ID + "-configloader");
    private static final Path configPath =
            Paths.get(
                    MetaAPI.instance().meta().configFolder()
                            + File.separator
                            + SwitchboardPlugin.PROJECT_ID
                            + File.separator
                            + SwitchboardPlugin.PROJECT_ID
                            + ".conf");
    private static final String defaultConfigPath = "source." + SwitchboardPlugin.PROJECT_ID + ".conf";
    private static HoconConfigurationLoader loader;
    private static SwitchboardConfig config;

    /** Load the configuration from the file. */
    public static void load() {
        loader = HoconConfigurationLoader.builder().path(configPath).build();
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
