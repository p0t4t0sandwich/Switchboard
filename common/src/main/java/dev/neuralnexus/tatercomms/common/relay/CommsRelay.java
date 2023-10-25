package dev.neuralnexus.tatercomms.common.relay;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.discord.DiscordBot;
import dev.neuralnexus.tatercomms.common.socket.Client;
import dev.neuralnexus.tatercomms.common.socket.Server;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.player.cache.PlayerCache;
import dev.neuralnexus.taterlib.common.relay.Message;
import dev.neuralnexus.taterlib.common.relay.MessageRelay;

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
    public void relayMessage(Message message) {
        CommsMessage commsMessage = (CommsMessage) message;

        // Set the chat to global if it is enabled
        // TODO: Rework into a per-user setting
        if (TaterCommsConfig.serverGlobalChatEnabledByDefault()
                && !commsMessage.getSender().getServerName().equals("discord")
                && commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())) {
            commsMessage.setGlobal(true);
        }

        // Relay the message to Discord
        if (discord != null && !commsMessage.getSender().getServerName().equals("discord")) {
            discord.sendMessage(commsMessage);
        }

        // Relay the message to the socket server
        if (socketClient != null && TaterCommsConfig.serverName().equals(commsMessage.getSender().getServerName()) &&
                !(TaterCommsConfig.serverUsingProxy()
                        && (commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())
                        || commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGIN.getIdentifier())
                        || commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGOUT.getIdentifier())))) {
            socketClient.sendMessage(commsMessage);
        }

        // Relay player messages to socket clients
        if (TaterCommsConfig.remoteEnabled()
                && commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())) {
            Server.sendMessageToAll(commsMessage);
        }

        // Send the message using proxy channels
        if (TaterCommsConfig.serverUsingProxy()
                && !TaterCommsConfig.remoteEnabled()
                && !commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())
                && !commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGIN.getIdentifier())
                && !commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_LOGOUT.getIdentifier())) {
            commsMessage.getSender().sendPluginMessage(commsMessage);
        }

        // Set the formatting for the message
        if (commsMessage.isRemote()) {
            commsMessage.setPlaceHolderMessage(TaterCommsConfig.formattingChat().get("remote"));
        } else if (commsMessage.isGlobal()) {
            commsMessage.setPlaceHolderMessage(TaterCommsConfig.formattingChat().get("global"));
        }

        // Relay messages to players on the server
        if (commsMessage.getChannel().equals(CommsMessage.MessageType.PLAYER_MESSAGE.getIdentifier())) {
            for (AbstractPlayer player : PlayerCache.getPlayersInCache()) {
                if (commsMessage.isGlobal() || commsMessage.isRemote()) {
                    player.sendMessage(commsMessage.applyPlaceHolders());
                } else if (TaterCommsConfig.formattingEnabled() || !player.getServerName().equals(commsMessage.getSender().getServerName())) {
                    player.sendMessage(commsMessage.applyPlaceHolders());
                }
            }
        }
    }
}
