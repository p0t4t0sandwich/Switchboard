package dev.neuralnexus.tatercomms.modules.discord.api;

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
