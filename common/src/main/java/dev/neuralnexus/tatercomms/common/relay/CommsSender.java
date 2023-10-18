package dev.neuralnexus.tatercomms.common.relay;

import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayerInventory;
import dev.neuralnexus.taterlib.common.abstractions.utils.Position;

import java.util.UUID;

public class CommsSender implements AbstractPlayer {
    private final String name;
    private final String prefix;
    private final String suffix;
    private final String displayName;
    private final UUID uuid;
    private final String serverName;

    /**
     * Constructor for the CommsSender class.
     * @param name The name
     * @param prefix The prefix
     * @param suffix The suffix
     * @param displayName The display name
     * @param uuid The UUID
     * @param serverName The server name
     */
    public CommsSender(String name, String prefix, String suffix, String displayName, UUID uuid, String serverName) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.displayName = displayName;
        this.uuid = uuid;
        this.serverName = serverName;
    }

    /**
     * Constructor for the CommsSender class.
     * @param player The player
     * @param serverName The server name
     */
    public CommsSender(AbstractPlayer player, String serverName) {
        this(player.getName(), player.getPrefix(), player.getSuffix(), player.getDisplayName(), player.getUUID(), serverName);
    }

    /**
     * Constructor for the CommsSender class.
     * @param player The player
     */
    public CommsSender(AbstractPlayer player) {
        this(player.getName(), player.getPrefix(), player.getSuffix(), player.getDisplayName(), player.getUUID(), player.getServerName());
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
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
    public Position getPosition() {
        return null;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public void setServerName(String s) {}

    @Override
    public void sendMessage(String s) {}

    @Override
    public void sendPluginMessage(String s, byte[] bytes) {}

    @Override
    public AbstractPlayerInventory getInventory() {
        return null;
    }

    @Override
    public void kickPlayer(String s) {}

    @Override
    public void setSpawn(Position position) {}

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }
}
