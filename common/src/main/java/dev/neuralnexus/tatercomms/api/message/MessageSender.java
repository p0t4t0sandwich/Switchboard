package dev.neuralnexus.tatercomms.api.message;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.inventory.PlayerInventory;
import dev.neuralnexus.taterlib.player.GameMode;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.utils.Location;

import java.util.UUID;

public class MessageSender implements Player {
    private final String name;
    private final String prefix;
    private final String suffix;
    private final String displayName;
    private final UUID uuid;
    private String serverName;

    /**
     * Constructor for the CommsSender class.
     *
     * @param name The name
     * @param prefix The prefix
     * @param suffix The suffix
     * @param displayName The display name
     * @param uuid The UUID
     * @param serverName The server name
     */
    public MessageSender(
            String name,
            String prefix,
            String suffix,
            String displayName,
            UUID uuid,
            String serverName) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.displayName = displayName;
        this.uuid = uuid;
        this.serverName = serverName;
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param player The player
     * @param serverName The server name
     */
    public MessageSender(Player player, String serverName) {
        this(
                player.getName(),
                player.getPrefix(),
                player.getSuffix(),
                player.getDisplayName(),
                player.getUniqueId(),
                serverName);
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param player The player
     */
    public MessageSender(Player player) {
        this(
                player.getName(),
                player.getPrefix(),
                player.getSuffix(),
                player.getDisplayName(),
                player.getUniqueId(),
                player.getServerName());
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param serverName The server name
     */
    public MessageSender(String serverName) {
        this.name = "";
        this.prefix = "";
        this.suffix = "";
        this.displayName = "";
        this.uuid = UUID.randomUUID();
        this.serverName = serverName;
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
    public String getIPAddress() {
        return null;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public void sendMessage(String message) {
        Player player =
                TaterAPIProvider.get().getServer().getOnlinePlayers().stream()
                        .filter(p -> p.getUniqueId().equals(this.uuid))
                        .findFirst()
                        .orElse(null);
        if (player == null) return;
        player.sendMessage(message);
    }

    @Override
    public void sendPluginMessage(String channel, byte[] bytes) {
        Player player =
                TaterAPIProvider.get().getServer().getOnlinePlayers().stream()
                        .filter(p -> p.getUniqueId().equals(this.uuid))
                        .findFirst()
                        .orElse(null);
        if (player == null) return;
        player.sendPluginMessage(channel, bytes);
    }

    /**
     * Sends a plugin message on behalf of the player.
     *
     * @param message The message
     */
    public void sendPluginMessage(Message message) {
        Player player =
                TaterAPIProvider.get().getServer().getOnlinePlayers().stream()
                        .filter(p -> p.getUniqueId().equals(this.uuid))
                        .findFirst()
                        .orElse(null);
        if (player == null) return;
        player.sendPluginMessage(message.getChannel(), message.toByteArray());
    }

    // ------------------------- Unused -------------------------

    @Override
    public int getEntityId() {
        return 0;
    }

    @Override
    public void remove() {}

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getCustomName() {
        return null;
    }

    @Override
    public void setCustomName(String s) {}

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
    public void teleport(Location location) {}

    @Override
    public void teleport(Entity entity) {}

    @Override
    public boolean hasPermission(int i) {
        return false;
    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public int getPing() {
        return -1;
    }

    @Override
    public void kickPlayer(String message) {}

    @Override
    public void setSpawn(Location location, boolean b) {}

    @Override
    public void setSpawn(Location location) {}

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public void setGameMode(GameMode gameMode) {}
}
