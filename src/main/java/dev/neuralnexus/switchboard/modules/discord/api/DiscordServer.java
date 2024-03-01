/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.discord.api;

import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.Collections;
import java.util.List;

/** Discord implementation of {@link SimpleServer}. */
public class DiscordServer implements SimpleServer {
    private final String guildId;
    private final String channelId;
    private final String name;

    public DiscordServer(net.dv8tion.jda.api.entities.Message message) {
        this.guildId = message.getGuild().getId();
        this.channelId = message.getChannel().getId();
        this.name = message.getGuild().getName();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String brand() {
        return "Discord";
    }

    @Override
    public List<SimplePlayer> onlinePlayers() {
        return Collections.emptyList();
    }
}
