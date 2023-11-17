package dev.neuralnexus.tatercomms.common.discord.player;

import dev.neuralnexus.taterlib.common.entity.Entity;
import dev.neuralnexus.taterlib.common.inventory.PlayerInventory;
import dev.neuralnexus.taterlib.common.player.Player;
import dev.neuralnexus.taterlib.common.utils.Location;
import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

/**
 * Abstracts a Discord user to a TaterPlayer.
 */
public class DiscordPlayer implements Player {
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

    @Override
    public int getEntityId() {
        return 0;
    }

    @Override
    public void remove() {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getCustomName() {
        return null;
    }

    @Override
    public void setCustomName(String s) {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public float getYaw() {
        return 0;
    }

    @Override
    public float getPitch() {
        return 0;
    }

    @Override
    public String getDimension() {
        return null;
    }

    @Override
    public String getBiome() {
        return null;
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public void teleport(Entity entity) {

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

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUniqueId() {
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

    @Override
    public boolean hasPermission(int i) {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendPluginMessage(String channel, byte[] message) {}

    /**
     * @inheritDoc
     */
    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kickPlayer(String message) {}

    @Override
    public void setSpawn(Location location, boolean b) {

    }

    @Override
    public void setSpawn(Location location) {

    }
}
