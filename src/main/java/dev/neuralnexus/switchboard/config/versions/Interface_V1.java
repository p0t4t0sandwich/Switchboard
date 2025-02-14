/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.Interface;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Required;

@ConfigSerializable
public class Interface_V1<T> implements Interface<T> {
    @Comment("The name of the interface")
    @Required
    private String name;

    @Comment("The type of the interface")
    @Required
    private String type;

    @Comment("The mode of the interface, can be \"source\", \"sink\", or \"both\"")
    @Required
    private Mode mode;

    @Comment("The configuration for this type of interface")
    private T config;

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public Mode mode() {
        return this.mode;
    }

    @Override
    public T config() {
        return this.config;
    }
}
