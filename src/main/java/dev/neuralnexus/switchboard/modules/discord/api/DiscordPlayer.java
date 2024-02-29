package dev.neuralnexus.switchboard.modules.discord.api;

import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

/** Discord implementation of {@link SimplePlayer}. */
public class DiscordPlayer implements SimplePlayer {
    private final User user;
    private final String name;
    private final String displayName;
    private final DiscordServer server;

    /**
     * Constructor.
     *
     * @param message The message
     */
    public DiscordPlayer(net.dv8tion.jda.api.entities.Message message) {
        this.user = message.getAuthor();
        this.name = this.user.getName();
        this.displayName = this.user.getEffectiveName();
        this.server = new DiscordServer(message);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public String ipAddress() {
        return "";
    }

    @Override
    public int ping() {
        return 0;
    }

    @Override
    public void kick(String message) {}

    @Override
    public UUID uuid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public SimpleServer server() {
        return this.server;
    }

    @Override
    public void sendMessage(String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    @Override
    public boolean hasPermission(int permissionLevel) {
        return false;
    }

    @Override
    public void sendPluginMessage(String channel, byte[] message) {}

    @Override
    public String prefix() {
        return "";
    }

    @Override
    public String suffix() {
        return "";
    }
}
