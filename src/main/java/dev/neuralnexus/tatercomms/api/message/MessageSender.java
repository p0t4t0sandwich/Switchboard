package dev.neuralnexus.tatercomms.api.message;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.UUID;

public class MessageSender implements SimplePlayer {
    private final String name;
    private final String prefix;
    private final String suffix;
    private final String displayName;
    private final UUID uuid;
    private final MessageServer server;

    /**
     * Constructor for the CommsSender class.
     *
     * @param name The name
     * @param prefix The prefix
     * @param suffix The suffix
     * @param displayName The display name
     * @param uuid The UUID
     * @param server The server name
     */
    public MessageSender(
            String name,
            String prefix,
            String suffix,
            String displayName,
            UUID uuid,
            SimpleServer server) {
        if (name == null) {
            name = "";
        }
        this.name = name;
        if (prefix == null) {
            prefix = "";
        }
        this.prefix = prefix;
        if (suffix == null) {
            suffix = "";
        }
        this.suffix = suffix;
        if (displayName == null) {
            displayName = "";
        }
        this.displayName = displayName;
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        this.uuid = uuid;
        this.server = new MessageServer(server);
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param player The player
     * @param server The server name
     */
    public MessageSender(SimplePlayer player, SimpleServer server) {
        this(
                player.name(),
                player.prefix(),
                player.suffix(),
                player.displayName(),
                player.uuid(),
                new MessageServer(server));
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
                player.server());
    }

    /**
     * Constructor for the CommsSender class.
     *
     * @param server The server name
     */
    public MessageSender(SimpleServer server) {
        this.name = "";
        this.prefix = "";
        this.suffix = "";
        this.displayName = "";
        this.uuid = UUID.randomUUID();
        this.server = new MessageServer(server);
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
        return this.server;
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
        TaterAPIProvider.get().getServer().onlinePlayers().stream()
                .filter(p -> p.uuid().equals(this.uuid))
                .findFirst()
                .ifPresent(p -> p.sendMessage(message));
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
    public void sendPluginMessage(String channel, byte[] bytes) {
        TaterAPIProvider.get().getServer().onlinePlayers().stream()
                .filter(p -> p.uuid().equals(this.uuid))
                .findFirst()
                .ifPresent(p -> p.sendPluginMessage(channel, bytes));
    }

    public void sendPluginMessage(Message message) {
        this.sendPluginMessage(message.channel().id(), message.toByteArray());
    }
}
