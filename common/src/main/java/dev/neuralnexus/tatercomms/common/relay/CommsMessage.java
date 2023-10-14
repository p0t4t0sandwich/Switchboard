package dev.neuralnexus.tatercomms.common.relay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.neuralnexus.tatercomms.common.listeners.player.CommonPlayerListener;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

/**
 * Class for relaying messages between the server and Discord
 */
public class CommsMessage {
    private final CommsSender sender;
    private final String message;

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param message The message
     */
    public CommsMessage(CommsSender sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Getter for the sender
     * @return The sender
     */
    public CommsSender getSender() {
        return this.sender;
    }

    /**
     * Getter for the message
     * @return The message
     */
    public String getMessage() {
        return this.message;
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
//        CommsMessage.MessageType messageType = CommsMessage.MessageType.fromIdentifier(channel);
        switch (channel) {
            case "tatercomms:player_advancement_finished":
                CommonPlayerListener.onPlayerAdvancementFinished(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_death":
                CommonPlayerListener.onPlayerDeath(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_login":
                CommonPlayerListener.onPlayerLogin(new Object[]{message.getSender()});
                break;
        }
    }

    /**
     * Enum for the different types of messages that can be sent
     */
    public enum MessageType {
        PLAYER_ADVANCEMENT_FINISHED("tatercomms:player_advancement_finished"),
        PLAYER_DEATH("tatercomms:player_death"),
        PLAYER_LOGIN("tatercomms:player_login");

        private final String id;

        MessageType(String id) {
            this.id = id;
        }

        public String getIdentifier() {
            return this.id;
        }

        static MessageType fromIdentifier(String id) {
            for (MessageType messageType : MessageType.values()) {
                if (messageType.getIdentifier().equals(id)) {
                    return messageType;
                }
            }
            return null;
        }
    }

    public byte[] toByteArray() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(gson.toJson(this));
        return out.toByteArray();
    }
}
