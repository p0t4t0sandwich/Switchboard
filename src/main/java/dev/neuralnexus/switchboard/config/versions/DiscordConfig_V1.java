/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Required;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class DiscordConfig_V1 {
    @Comment("The Discord bot token")
    @Required
    private String token;

    @Comment("The Discord guild ID (otherwise known as server ID)")
    @Required
    @Setting("guildId")
    private String guildId;

    @Comment(
            "The Discord channel ID (should be a text channel in most cases, threads and forums might work to various degrees)")
    @Required
    @Setting("channelId")
    private String channelId;

    public String token() {
        return this.token;
    }

    public String guildId() {
        return this.guildId;
    }

    public String channelId() {
        return this.channelId;
    }
}
