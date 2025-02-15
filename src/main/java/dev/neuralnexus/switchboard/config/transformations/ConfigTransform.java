package dev.neuralnexus.switchboard.config.transformations;

import dev.neuralnexus.switchboard.logger.Logger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;

import static org.spongepowered.configurate.NodePath.path;

public class ConfigTransform {
    private static final Logger logger = Logger.create("Switchboard-ConfigTransform");

    private static final int VERSION_LATEST = 2;

    private ConfigTransform() {}

    public static ConfigurationTransformation.Versioned create() {
        return ConfigurationTransformation.versionedBuilder()
                .addVersion(VERSION_LATEST, oneToTwo()) // syntax: target version, latest version
                .addVersion(1, initialTransform())
                .build();
    }

    public static ConfigurationTransformation initialTransform() {
        return ConfigurationTransformation.builder()
                .addAction(path("version"), (path, value) -> null)
                .build();
    }

    public static ConfigurationTransformation oneToTwo() {
        return ConfigurationTransformation.builder()
                .addAction(path("version"), (path, value) -> {
                    // TODO: Set up transformations for version 1 -> 2
                    value.set(2);
                    return null;
                })
                .build();
    }

    public static <N extends ConfigurationNode> N updateNode(final N node) throws ConfigurateException {
        if (!node.virtual()) { // we only want to migrate existing data
            final ConfigurationTransformation.Versioned trans = create();
            final int startVersion = trans.version(node);
            trans.apply(node);
            final int endVersion = trans.version(node);
            if (startVersion != endVersion) { // we might not have made any changes
                logger.info("Updated config schema from " + startVersion + " to " + endVersion);
            }
        }
        return node;
    }
}
