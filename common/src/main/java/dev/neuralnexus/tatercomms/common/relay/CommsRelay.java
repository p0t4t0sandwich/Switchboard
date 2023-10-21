package dev.neuralnexus.tatercomms.common.relay;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.socket.Client;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
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
     * socketClient: The socket client
     */
    private static MessageRelay singleton = null;
    private final HashMap<String, String> formatting;
    private final DiscordBot discord;
    private final Client socketClient;

    /**
     * Constructor for the MessageRelay class.
     */
    public CommsRelay(HashMap<String, String> formatting, DiscordBot discord, Client socketClient) {
        singleton = this;
        this.formatting = formatting;
        this.discord = discord;
        this.socketClient = socketClient;
    }

    /**
     * Getter for the singleton instance of the MessageRelay class.
     * @return The singleton instance
     */
    public static MessageRelay getInstance() {
        return singleton;
    }

    /**
     * Relays a CommsMessage from a source to all other sources.
     * @param message The message
     */
    public void relayMessage(CommsMessage message) {
        // Relay the message to Discord
        if (discord != null && !message.getSender().getServerName().equals("discord")) {
            discord.sendMessage(message);
        }

        // Relay the message to the socket server
        if (socketClient != null) {
            socketClient.sendMessage(message);
        }

        // Relay the message to the proxy
        if (TaterCommsConfig.serverUsingProxy() && !TaterCommsConfig.remoteEnabled()) {
            message.getSender().sendPluginMessage(message);
        }

        // Relay external messages to the players
        if ((!message.getSender().getServerName().equals(TaterCommsConfig.serverName())
                || TaterLib.cancelChat) && message.getChannel().equals("tc:p_msg")) {
            for (AbstractPlayer player : PlayerCache.getPlayersInCache()) {
                player.sendMessage(message.getMessage());
            }
        }
    }

    @Override
    public void sendPlayerMessage(AbstractPlayer abstractPlayer, String s, String s1, boolean b) {

    }

    @Override
    public void sendSystemMessage(String s, String s1) {

    }
}
