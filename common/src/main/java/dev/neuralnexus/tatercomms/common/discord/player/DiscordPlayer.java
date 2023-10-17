package dev.neuralnexus.tatercomms.common.discord.player;

import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayerInventory;
import dev.neuralnexus.taterlib.common.abstractions.utils.Position;
import dev.neuralnexus.taterlib.common.player.cache.PlayerCache;
import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

/**
 * Abstracts a Discord user to a TaterPlayer.
 */
public class DiscordPlayer implements AbstractPlayer {
    private final User user;
    private final String name;
    private final String displayName;
    private final String serverName;

    /**
     * Constructor.
     * @param user The Discord user.
     */
    public DiscordPlayer(User user) {
        this.user = user;
        this.name = user.getName();
        this.displayName = user.getEffectiveName();

        this.serverName = "discord";

        // TODO: Set up account linking, and get the UUID and name from the database.
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUUID() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerName() {
        return this.serverName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setServerName(String serverName) {}

    /**
     * @inheritDoc
     */
    @Override
    public void sendMessage(String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }

    /**
     * @inheritDoc
     */
    @Override
    public AbstractPlayerInventory getInventory() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kickPlayer(String message) {}

    @Override
    public void setSpawn(Position position) {}
}
