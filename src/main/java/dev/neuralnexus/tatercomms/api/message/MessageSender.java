package dev.neuralnexus.tatercomms.api.message;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.Optional;
import java.util.UUID;

public class MessageSender implements SimplePlayer {
    private final String name;
    private final String prefix;
    private final String suffix;
    private final String displayName;
    private final UUID uuid;
    private final String serverName;

    /**
     * Constructor for the CommsSender class.
     *
     * @param name        The name
     * @param prefix      The prefix
     * @param suffix      The suffix
     * @param displayName The display name
     * @param uuid        The UUID
     * @param serverName  The server name
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
     * @param player     The player
     * @param serverName The server name
     */
    public MessageSender(SimplePlayer player, String serverName) {
        this(
                player.name(),
                player.prefix(),
                player.suffix(),
                player.displayName(),
                player.uuid(),
                serverName);
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param player The player
     */
    public MessageSender(SimplePlayer player) {
        this(
                player.name(),
                player.prefix(),
                player.suffix(),
                player.displayName(),
                player.uuid(),
                player.server().name());
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
    public String name() {
        return this.name;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public SimpleServer server() {
        return TaterAPIProvider.get().getServer();
    }

    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public boolean hasPermission(int permissionLevel) {
        return false;
    }

    @Override
    public String prefix() {
        return this.prefix;
    }

    @Override
    public String suffix() {
        return this.suffix;
    }

    @Override
    public void sendMessage(String message) {
        Optional<SimplePlayer> player =
                TaterAPIProvider.get().getServer().onlinePlayers().stream()
                        .filter(p -> p.uuid().equals(this.uuid))
                        .findFirst();
        if (!player.isPresent()) return;
        player.get().sendMessage(message);
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
    public void kick(String message) {
    }

    @Override
    public void sendPluginMessage(String channel, byte[] bytes) {
        Optional<SimplePlayer> player =
                TaterAPIProvider.get().getServer().onlinePlayers().stream()
                        .filter(p -> p.uuid().equals(this.uuid))
                        .findFirst();
        if (!player.isPresent()) return;
        player.get().sendPluginMessage(channel, bytes);
    }

    /**
     * Sends a plugin message on behalf of the player.
     *
     * @param message The message
     */
    public void sendPluginMessage(Message message) {
        Optional<SimplePlayer> player =
                TaterAPIProvider.get().getServer().onlinePlayers().stream()
                        .filter(p -> p.uuid().equals(this.uuid))
                        .findFirst();
        if (!player.isPresent()) return;
        player.get().sendPluginMessage(message.getChannel(), message.toByteArray());
    }
}
