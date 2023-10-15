package dev.neuralnexus.tatercomms.common.relay;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.discord.player.DiscordPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.common.player.cache.PlayerCache;
import dev.neuralnexus.taterlib.common.relay.MessageRelay;

import java.util.HashMap;

/**
 * Class for relaying messages between the server and Discord.
 */
public class CommsRelay implements MessageRelay {
    /**
     * Properties of the MessageRelay class.
     * singleton: The singleton instance of the MessageRelay class
     * taterPlayerCache: A cache of TaterPlayer objects
     * discord: The JDA instance
     */
    private static MessageRelay singleton = null;
    private final HashMap<String, String> formatting;
    private final DiscordBot discord;

    /**
     * Constructor for the MessageRelay class.
     */
    public CommsRelay(HashMap<String, String> formatting, DiscordBot discord) {
        singleton = this;
        // Config option: relay.discord
        // Config option: relay.remote
        // Config option: relay.global

        this.formatting = formatting;
        this.discord = discord;
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
     * @param isCancelled Whether the message was cancelled
     */
    @Override
    public void sendPlayerMessage(AbstractPlayer player, String server, String message, boolean isCancelled) {
        // Message
        String formattedMessage = player.parsePlaceholders(this.formatting.get("global")).parseString("message", message).getResult();
        TaterComms.useLogger(PlaceholderParser.stripSectionSign(formattedMessage));

        // Relay message to each TaterPlayer on every other server (Global chat)
        for (AbstractPlayer abstractPlayer : PlayerCache.getPlayersInCache()) {
            if (isCancelled || !abstractPlayer.getServerName().equals(server)) {
                abstractPlayer.sendMessage(formattedMessage);
            }
        }

        // Relay message to Discord
        if (this.discord != null) {
            this.discord.sendSystemMessage(server, PlaceholderParser.stripSectionSign(formattedMessage));
        }

        // Relay message to remote server
        // TODO: Relay message to remote server
    }

    /**
     * Relay a system message from the Minecraft server to Discord.
     * @param server The server
     * @param message The message
     */
    @Override
    public void sendSystemMessage(String server, String message) {
        // Relay system message to Discord
        if (this.discord != null) {
            this.discord.sendSystemMessage(server, message);
        }

        // TODO: Relay system message to remote server
    }

    /**
     * Relay a player switch event from the Minecraft server to Discord.
     * @param player The player
     * @param fromServer The server the player is switching from
     * @param toServer The server the player is switching to
     */
    public void sendPlayerServerSwitch(AbstractPlayer player, String fromServer, String toServer) {
        if (fromServer != null) {
            // Relay player logout to Discord
            this.sendSystemMessage(player.getServerName(), player.getName() + " left the game");
        }

        // Relay player login to Discord
        this.sendSystemMessage(toServer, player.getName() + " joined the game");
    }

    /**
     * Relays a message from Discord, or a remote server, to the Minecraft server.
     * @param message The message
     */
    public void receiveMessage(AbstractPlayer player, String server, String message) {
        String formattedMessage;
        if (player instanceof DiscordPlayer) {
            formattedMessage = player.parsePlaceholders(this.formatting.get("discord")).parseString("message", message).getResult();
        } else {
            formattedMessage = player.parsePlaceholders(this.formatting.get("remote")).parseString("message", message).getResult();
        }
        TaterComms.useLogger(PlaceholderParser.stripSectionSign(formattedMessage));

        // Relay message to each TaterPlayer on the server
        for (AbstractPlayer abstractPlayer : PlayerCache.getPlayersInCache()) {
            if (abstractPlayer.getServerName().equals(server)) {
                abstractPlayer.sendMessage(formattedMessage);
            }
        }
    }
}
