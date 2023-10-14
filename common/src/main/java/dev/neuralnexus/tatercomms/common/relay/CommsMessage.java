package dev.neuralnexus.tatercomms.common.relay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dev.neuralnexus.tatercomms.common.listeners.player.CommonPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.CommonServerListener;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

/**
 * Class for relaying messages between the server and Discord
 */
public class CommsMessage {
    private final CommsSender sender;
    private final String message;
    private final boolean isCancelled;

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param message The message
     * @param isCancelled Whether the message was cancelled
     */
    public CommsMessage(CommsSender sender, String message, boolean isCancelled) {
        this.sender = sender;
        this.message = message;
        this.isCancelled = isCancelled;
    }


    public CommsSender getSender() {
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    static Gson gson = new GsonBuilder().create();

    /**
     * Message channel parser
     * @param channel The channel
     * @param data The byte array data.
     */
    public static void parseMessageChannel(String channel, byte[] data) {
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String json = in.readUTF();
        CommsMessage message = gson.fromJson(json, CommsMessage.class);

        CommsMessage.MessageType messageType = CommsMessage.MessageType.valueOf(channel);

        switch (messageType) {
            case PLAYER_ADVANCEMENT_FINISHED:
                CommonPlayerListener.onPlayerAdvancementFinished(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_DEATH:
                CommonPlayerListener.onPlayerDeath(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_LOGIN:
                CommonPlayerListener.onPlayerLogin(new Object[]{message.getSender()});
                break;
            case PLAYER_LOGOUT:
                CommonPlayerListener.onPlayerLogout(new Object[]{message.getSender().getServerName()});
                break;
            case SERVER_STARTED:
                CommonServerListener.onServerStarted(new Object[]{message.getSender().getServerName()});
                break;
            case SERVER_STOPPED:
                break;
        }
    }

    /**
     * Enum for the different types of messages that can be sent
     */
    public enum MessageType {
        PLAYER_ADVANCEMENT_FINISHED("tatercomms:player_advancement_finished"),
        PLAYER_DEATH("tatercomms:player_death"),
        PLAYER_LOGIN("tatercomms:player_login"),
        PLAYER_LOGOUT("tatercomms:player_logout"),
        SERVER_STARTED("tatercomms:server_started"),
        SERVER_STOPPED("tatercomms:server_stopped");

        private final String id;

        MessageType(String id) {
            this.id = id;
        }

        public String getIdentifier() {
            return this.id;
        }
    }
}
