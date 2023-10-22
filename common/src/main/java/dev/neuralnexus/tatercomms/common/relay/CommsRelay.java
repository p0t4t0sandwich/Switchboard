package dev.neuralnexus.tatercomms.common.relay;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.socket.Client;
import dev.neuralnexus.tatercomms.common.socket.Server;
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
     * Properties of the MessageRelay class
     * formatting: The formatting for the messages
     * discord: The JDA instance
     * socketClient: The socket client
     */
    private final HashMap<String, String> formatting;
    private final DiscordBot discord;
    private final Client socketClient;

    /**
     * Constructor for the MessageRelay class.
     */
    public CommsRelay(HashMap<String, String> formatting, DiscordBot discord, Client socketClient) {
        this.formatting = formatting;
        this.discord = discord;
        this.socketClient = socketClient;
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

        // Relay messages to remote servers
        if (TaterCommsConfig.remoteEnabled()
                && !message.getSender().getServerName().equals(TaterCommsConfig.serverName())
                && message.getChannel().equals("tc:p_msg")) {
            Server.sendMessageToAll(message);
        }

        // Relay the message to the proxy
        if (TaterCommsConfig.serverUsingProxy()
                && !TaterCommsConfig.remoteEnabled()
                && !message.getChannel().equals("tc:p_msg")) {
            message.getSender().sendPluginMessage(message);
        }

        // Relay external messages to the players
        if ((!message.getSender().getServerName().equals(TaterCommsConfig.serverName())
                || TaterLib.cancelChat) && message.getChannel().equals("tc:p_msg")) {
            for (AbstractPlayer player : PlayerCache.getPlayersInCache()) {
                if (TaterCommsConfig.formattingEnabled() || !player.getServerName().equals(message.getSender().getServerName())) {
                    player.sendMessage(message.getMessage());
                }
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
