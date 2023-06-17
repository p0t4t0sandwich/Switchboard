package ca.sperrer.p0t4t0sandwich.tatercomms.common.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;

import java.util.UUID;

/**
 * The interface for a TaterPlayer
 */
public interface TaterPlayer {
    /**
     * Get the UUID of the player
     * @return The UUID of the player
     */
    UUID getUUID();

    /**
     * Get the name of the player
     * @return The name of the player
     */
    String getName();

    /**
     * Get the display name of the player
     * @return The display name of the player
     */
    String getDisplayName();

    /**
     * Get the server the player is on
     * @return The server the player is on
     */
    String getServerName();

    /**
     * Set the server the player is on
     * @param serverName The server the player is on
     */
    void setServerName(String serverName);

    /**
     * Send a message to the player
     * @param message The message to send
     */
    void sendMessage(String message);

    /**
     * Get the prefix of the player
     * @return The prefix of the player
     */
    default String getPrefix() {
        LuckPermsHook luckPermsHook = LuckPermsHook.getInstance();
        return luckPermsHook != null ? luckPermsHook.getPrefix(this) : "";
    }

    /**
     * Get the suffix of the player
     * @return The suffix of the player
     */
    default String getSuffix() {
        LuckPermsHook luckPermsHook = LuckPermsHook.getInstance();
        return luckPermsHook != null ? luckPermsHook.getSuffix(this) : "";
    }
}
