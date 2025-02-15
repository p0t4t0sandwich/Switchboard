/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.Interface;
import dev.neuralnexus.switchboard.config.Relay;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Required;

import java.util.List;
import java.util.Map;

@ConfigSerializable
public class Relay_V1 implements Relay {
    @Comment("Config version, DO NOT CHANGE THIS")
    @Required
    private int version = 1;

    @Comment("List of defined Sources and Sinks")
    private List<Interface_V1<?>> interfaces = List.of();

    @Comment("Defined channels mapped as SourceName:SinkName")
    private Map<String, String> channels = Map.of();

    @Override
    public int version() {
        return this.version;
    }

    @Override
    public List<Interface<?>> interfaces() {
        return List.copyOf(this.interfaces);
    }

    @Override
    public Map<String, String> channels() {
        return this.channels;
    }
}
