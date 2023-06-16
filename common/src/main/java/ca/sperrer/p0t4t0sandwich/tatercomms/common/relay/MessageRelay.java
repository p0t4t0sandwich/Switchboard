package ca.sperrer.p0t4t0sandwich.tatercomms.common.relay;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.discord.DiscordBot;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.discord.player.DiscordTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;

import java.util.HashMap;
import java.util.UUID;

public class MessageRelay {
    /**
     * Properties of the MessageRelay class.
     * singleton: The singleton instance of the MessageRelay class
     * taterPlayerCache: A cache of TaterPlayer objects
     * discord: The JDA instance
     */
    private static MessageRelay singleton = null;
    private final HashMap<UUID, TaterPlayer> taterPlayerCache = new HashMap<>();
    private final DiscordBot discord;

    /**
     * Constructor for the MessageRelay class.
     */
    public MessageRelay(DiscordBot discord) {
        singleton = this;
        // Config option: relay.discord
        // Config option: relay.remote
        // Config option: relay.global

        this.discord = discord;
    }

    /**
     * Getter for the TaterPlayer cache.
     * @param uuid The UUID of the player
     * @return The TaterPlayer cache
     */
    public TaterPlayer getTaterPlayerFromCache(UUID uuid) {
        return this.taterPlayerCache.get(uuid);
    }

    /**
     * Setter for the TaterPlayer cache.
     * @param uuid The UUID of the player
     * @param taterPlayer The TaterPlayer object
     */
    public void setTaterPlayerInCache(UUID uuid, TaterPlayer taterPlayer) {
        this.taterPlayerCache.put(uuid, taterPlayer);
    }

    /**
     * Removes a TaterPlayer object from the cache.
     * @param uuid The UUID of the player
     */
    public void removeTaterPlayerFromCache(UUID uuid) {
        this.taterPlayerCache.remove(uuid);
    }

    /**
     * Getter for the singleton instance of the MessageRelay class.
     * @return The singleton instance
     */
    public static MessageRelay getInstance() {
        return singleton;
    }

    /**
     * Relays a message from the Minecraft server to Discord, or to a remote server.
     * @param player The player
     * @param server The server
     * @param message The message
     */
    public void sendMessage(TaterPlayer player, String server, String message) {
        // Relay message to each TaterPlayer on every other server (Global chat)
        for (TaterPlayer taterPlayer : this.taterPlayerCache.values()) {
            if (!taterPlayer.getServerName().equals(server)) {
                taterPlayer.sendMessage("§a[G] §r" + player.getDisplayName() + ": " + message);
            }
        }

        // Relay message to Discord
        if (this.discord != null) {
            this.discord.sendPlayerMessage(player, server, message);
        }

        // Relay message to remote server
        // TODO: Relay message to remote server
    }

    /**
     * Relay a system message from the Minecraft server to Discord.
     * @param server The server
     * @param message The message
     */
    public void sendSystemMessage(String server, String message) {
        // Relay system message to Discord
        if (this.discord != null) {
            this.discord.sendSystemMessage(server, message);
        }

        // TODO: Relay system message to remote server
    }

    /**
     * Relay a player join event from the Minecraft server to Discord.
     * @param player The player
     * @param server The server
     */
    public void sendPlayerLogin(TaterPlayer player, String server) {
        // Relay player join to Discord
        this.sendMessage(player, server, "joined the game");
    }

    /**
     * Relay a player quit event from the Minecraft server to Discord.
     * @param player The player
     * @param server The server
     */
    public void sendPlayerLogout(TaterPlayer player, String server) {
        // Relay player quit to Discord
        this.sendMessage(player, server,  "left the game");
    }

    /**
     * Relay a player switch event from the Minecraft server to Discord.
     * @param player The player
     * @param fromServer The server the player is switching from
     * @param toServer The server the player is switching to
     */
    public void sendPlayerServerSwitch(TaterPlayer player, String fromServer, String toServer) {
        if (fromServer != null) {
            // Relay player logout to Discord
            this.sendPlayerLogout(player, fromServer);
        }

        // Relay player login to Discord
        this.sendPlayerLogin(player, toServer);
    }

    /**
     * Relays a message from Discord, or a remote server, to the Minecraft server.
     * @param message The message
     */
    public void receiveMessage(TaterPlayer player, String server, String message) {
        // Relay message to each TaterPlayer on the server
        for (TaterPlayer taterPlayer : this.taterPlayerCache.values()) {
            if (taterPlayer.getServerName().equals(server)) {
                if (player instanceof DiscordTaterPlayer) {
                    // TODO: placeholers for messages
                    taterPlayer.sendMessage("§9[D] §r" + player.getDisplayName() + ": " + message);
                } else {
                    taterPlayer.sendMessage("§c[R] §r" + player.getDisplayName() + ": " + message);
                }
            }
        }
    }
}
