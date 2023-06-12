package ca.sperrer.p0t4t0sandwich.tatercomms.common.discord.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

public class DiscordTaterPlayer implements TaterPlayer {
    private final User user;
    private final String name;
    private final String displayName;
    private final UUID uuid;
    private final String serverName;

    public DiscordTaterPlayer(User user) {
        this.user = user;
        this.name = user.getName();
        this.displayName = user.getEffectiveName();
        this.uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");

        this.serverName = "Discord";

        // TODO: Set up account linking, and get the UUID and name from the database.
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getServerName() {
        return "Discord";
    }

    @Override
    public void setServerName(String serverName) {}

    @Override
    public void sendMessage(String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }
}
