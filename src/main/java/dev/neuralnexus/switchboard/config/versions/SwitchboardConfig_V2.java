/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.SwitchboardConfig;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Required;

/** A class for Switchboard configuration. */
@ConfigSerializable
public class SwitchboardConfig_V2 implements SwitchboardConfig {
    @Comment("Config version, DO NOT CHANGE THIS")
    @Required
    private int version = 2;

    @Override
    public int version() {
        return this.version;
    }
}
