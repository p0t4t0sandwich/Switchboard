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
     * discord: The JDA instance
     * socketClient: The socket client
     */
    private final DiscordBot discord;
    private final Client socketClient;

    /**
     * Constructor for the MessageRelay class.
     */
    public CommsRelay(DiscordBot discord, Client socketClient) {
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
        if (socketClient != null && TaterCommsConfig.serverName().equals(message.getSender().getServerName()) &&
                !(TaterCommsConfig.serverUsingProxy()
                        && (message.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())
                        || message.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGIN.getIdentifier())
                        || message.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGOUT.getIdentifier()))
        )) {
            socketClient.sendMessage(message);
        }

        // Relay player messages to socket clients
        if (TaterCommsConfig.remoteEnabled()
                && message.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())) {
            Server.sendMessageToAll(message);
        }

        // Send the message using proxy channels
        if (TaterCommsConfig.serverUsingProxy()
                && !TaterCommsConfig.remoteEnabled()
                && !message.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())
                && !message.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGIN.getIdentifier())
                && !message.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGOUT.getIdentifier())) {
            message.getSender().sendPluginMessage(message);
        }

        // Relay messages to players on the server
        if (message.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())) {
            if (!TaterCommsConfig.serverName().equals(message.getSender().getServerName()) || TaterCommsConfig.formattingEnabled()) {
                for (AbstractPlayer player : PlayerCache.getPlayersInCache()) {
                    // Check if the sender and the player are on the same server
                    // If they are, check if formatting is enabled, and if it is, let it through
                    if (TaterCommsConfig.formattingEnabled() || !player.getServerName().equals(message.getSender().getServerName())) {
                        player.sendMessage(message.applyPlaceHolders());
                    }
                }
            }
        }
    }

    @Override
    public void sendPlayerMessage(AbstractPlayer abstractPlayer, String s, String s1, boolean b) {}

    @Override
    public void sendSystemMessage(String s, String s1) {}
}
