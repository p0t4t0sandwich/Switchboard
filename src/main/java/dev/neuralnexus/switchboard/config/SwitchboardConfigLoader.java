/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.serializers.InterfaceSerializer;
import dev.neuralnexus.switchboard.config.versions.DiscordConfig_V1;
import dev.neuralnexus.switchboard.config.versions.Relay_V1;
import dev.neuralnexus.switchboard.config.versions.SwitchboardConfig_V1;
import dev.neuralnexus.switchboard.config.versions.VersionedConfig;
import dev.neuralnexus.switchboard.logger.Logger;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** A class for loading Switchboard configuration. */
@SuppressWarnings("SwitchStatementWithTooFewBranches")
public final class SwitchboardConfigLoader {
    private static final Logger logger = Logger.create(Switchboard.PROJECT_ID + "-configloader");
    private static Path configPath =
            Paths.get(".").toAbsolutePath().normalize().resolve(Switchboard.PROJECT_ID);
    private static SwitchboardConfig config;
    private static List<Relay> relays = new ArrayList<>();
    private static final Map<String, Class<?>> typeRegistry = new HashMap<>();

    static {
        typeRegistry.put("discord", DiscordConfig_V1.class);
    }

    public static void registerType(String type, Class<?> clazz) {
        typeRegistry.put(type, clazz);
    }

    public static Class<?> getType(String type) {
        return typeRegistry.get(type);
    }

    public static void setBasePath(Path path) {
        configPath = path.resolve(Switchboard.PROJECT_ID);
    }

    private static HoconConfigurationLoader createLoader(Path path) {
        return HoconConfigurationLoader.builder()
                .path(path)
                .defaultOptions(
                        opts ->
                                opts.serializers(
                                        build ->
                                                build.register(
                                                        Interface.class,
                                                        InterfaceSerializer.INSTANCE)))
                .build();
    }

    private static CommentedConfigurationNode loadNode(Path path) {
        HoconConfigurationLoader loader = createLoader(path);
        CommentedConfigurationNode node = null;
        try {
            node = loader.load();
        } catch (ConfigurateException e) {
            logger.error("An error occurred while loading the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
            }
        }
        return node;
    }

    private static void saveNode(CommentedConfigurationNode node, Path path) {
        HoconConfigurationLoader loader = createLoader(path);
        try {
            loader.save(node);
        } catch (ConfigurateException e) {
            logger.error("An error occurred while saving the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: ", e.getCause());
            }
        }
    }

    /** Load the configuration from the file. */
    private static void loadConfig() {
        CommentedConfigurationNode node =
                loadNode(configPath.resolve(Switchboard.PROJECT_ID + ".conf"));
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

        saveNode(node, configPath.resolve(Switchboard.PROJECT_ID + ".conf"));
    }

    /** Load a relay from its config file */
    public static Relay loadRelay(Path relayPath) {
        CommentedConfigurationNode node = loadNode(relayPath);
        if (node == null) {
            return null;
        }

        Relay relay = null;
        int version = VersionedConfig.tryGetVersion(node, logger);
        switch (version) {
            case 1:
                try {
                    relay = node.get(Relay_V1.class);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while loading the relay configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
                break;
            default:
                logger.error(
                        "Unknown configuration version: " + version + ", defaulting to version 1");
                relay = new Relay_V1();
                try {
                    node.set(Relay_V1.class, relay);
                } catch (SerializationException e) {
                    logger.error(
                            "An error occurred while updating the configuration: "
                                    + e.getMessage());
                    if (e.getCause() != null) {
                        logger.error("Caused by: ", e.getCause());
                    }
                }
        }

        saveNode(node, relayPath);

        return relay;
    }

    /** Load the relays from their config files */
    public static void loadRelays() {
        // Grab all *.conf files in the relays directory
        Path relayPath = configPath.resolve("relays");
        List<Path> relayPaths = List.of();
        if (Files.exists(relayPath)) {
            try {
                relayPaths =
                        Files.walk(relayPath)
                                .filter(Files::isRegularFile)
                                .filter(path -> path.toString().endsWith(".conf"))
                                .toList();
            } catch (IOException e) {
                logger.error("An error occurred while loading the relays: " + e.getMessage());
                if (e.getCause() != null) {
                    logger.error("Caused by: ", e.getCause());
                }
            }
        } else {
            try {
                Files.createDirectories(relayPath);
                // TODO: Copy in example relay(s)
            } catch (IOException e) {
                logger.error(
                        "An error occurred while creating the relays directory: " + e.getMessage());
                if (e.getCause() != null) {
                    logger.error("Caused by: ", e.getCause());
                }
            }
        }

        Relay relay;
        for (Path path : relayPaths) {
            relay = loadRelay(path);
            if (relay != null) {
                relays.add(relay);
            }
        }
    }

    public static void load() {
        loadConfig();
        loadRelays();
    }

    /** Unload the configuration. */
    public static void unload() {
        config = null;
        relays = null;
    }

    private static void saveConfig() {
        if (config == null) {
            return;
        }
        CommentedConfigurationNode node =
                loadNode(configPath.resolve(Switchboard.PROJECT_ID + ".conf"));
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

        saveNode(node, configPath.resolve(Switchboard.PROJECT_ID + ".conf"));
    }

    /** Save the configurations */
    public static void save() {
        saveConfig();
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

    /**
     * Get the loaded relays.
     *
     * @return The loaded relays.
     */
    public static List<Relay> relays() {
        if (relays == null) {
            load();
        }
        return relays;
    }
}
