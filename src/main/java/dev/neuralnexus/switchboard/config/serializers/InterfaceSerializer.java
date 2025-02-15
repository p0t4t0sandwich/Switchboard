/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config.serializers;

import dev.neuralnexus.switchboard.config.Interface;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.config.versions.Interface_V1;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class InterfaceSerializer implements TypeSerializer<Interface> {
    public static final InterfaceSerializer INSTANCE = new InterfaceSerializer();

    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String MODE = "mode";

    private InterfaceSerializer() {}

    private ConfigurationNode nonVirtualNode(final ConfigurationNode source, final Object... path)
            throws SerializationException {
        if (!source.hasChild(path)) {
            throw new SerializationException(
                    "Required field " + Arrays.toString(path) + " was not present in node");
        }
        return source.node(path);
    }

    @Override
    public Interface<?> deserialize(
            final @NotNull Type type, final @NotNull ConfigurationNode source)
            throws SerializationException {
        final String typeStr = nonVirtualNode(source, TYPE).getString();
        final String nameStr = nonVirtualNode(source, NAME).getString();
        final String modeStr = nonVirtualNode(source, MODE).getString();
        if (typeStr == null || nameStr == null || modeStr == null) {
            throw new SerializationException("Missing required fields, or they are not strings");
        }
        final Interface.Mode mode = Interface.Mode.valueOf(modeStr.toUpperCase());

        Class<?> typeClass = SwitchboardConfigLoader.getType(typeStr);
        if (typeClass == null) {
            throw new SerializationException("Unknown interface type: " + typeStr);
        }
        return new Interface_V1<>(typeStr, nameStr, mode, source.node("config").get(typeClass));
    }

    @Override
    public void serialize(
            final @NotNull Type type,
            final @Nullable Interface inf,
            final @NotNull ConfigurationNode target)
            throws SerializationException {
        if (inf == null) {
            target.raw(null);
            return;
        }
        target.node(NAME).set(inf.name());
        target.node(TYPE).set(inf.type());
        target.node(MODE).set(inf.mode().toString());
        target.node("config").set(inf.config());
    }
}
