package dev.neuralnexus.tatercomms.api.message;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.player.SimplePlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Message object.
 */
public class Message {
    static Gson gson = new GsonBuilder().create();
    private final MessageSender sender;
    private final String channel;
    private final String message;
    private final long timeStamp;
    private String placeHolderMessage = "";
    private HashMap<String, String> placeHolders = new HashMap<>();
    private boolean isRemote = false;
    private boolean isGlobal = false;

    /**
     * Constructor for the CommsMessage class
     *
     * @param sender  The sender
     * @param channel The channel
     * @param message The message
     */
    public Message(
            MessageSender sender,
            String channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
        this.placeHolderMessage = placeHolderMessage;
        this.placeHolders = placeHolders;
        placeHolders.put("message", message);
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * Constructor for the CommsMessage class
     *
     * @param sender  The sender
     * @param channel The channel
     * @param message The message
     */
    public Message(
            MessageSender sender,
            Message.MessageType channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this(sender, channel.id(), message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     *
     * @param serverName The server name
     * @param channel    The channel
     * @param message    The message
     */
    public Message(
            String serverName,
            String channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this(new MessageSender(serverName), channel, message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     *
     * @param serverName The server name
     * @param channel    The channel
     * @param message    The message
     */
    public Message(
            String serverName,
            Message.MessageType channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this(
                new MessageSender(serverName),
                channel.id(),
                message,
                placeHolderMessage,
                placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     *
     * @param sender  The sender
     * @param channel The channel
     * @param message The message
     */
    public Message(
            SimplePlayer sender,
            String channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this(new MessageSender(sender), channel, message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     *
     * @param sender  The sender
     * @param channel The channel
     * @param message The message
     */
    public Message(
            SimplePlayer sender,
            MessageType channel,
            String message,
            String placeHolderMessage,
            HashMap<String, String> placeHolders) {
        this(new MessageSender(sender), channel.id(), message, placeHolderMessage, placeHolders);
    }

    /**
     * Creates a CommsMessage from a byte array
     *
     * @param data The byte array
     * @return The CommsMessage
     */
    public static Message fromByteArray(byte[] data) {
        try {
            try {
                ByteArrayDataInput in = ByteStreams.newDataInput(data);
                String json = in.readUTF();
                return gson.fromJson(json, Message.class);
            } catch (Exception e) {
                return gson.fromJson(new String(data), Message.class);
            }
        } catch (Exception e) {
            // TODO: Make this less jank
            try {
                // Fabric Support
                return gson.fromJson(
                        new String(Arrays.copyOfRange(data, 4, data.length)), Message.class);
            } catch (Exception ex) {
                // Forge Support
                return gson.fromJson(
                        new String(Arrays.copyOfRange(data, 7, data.length)), Message.class);
            }
        }
    }

    /**
     * Creates a CommsMessage from a JSON string
     *
     * @param json The JSON string
     * @return The CommsMessage
     */
    public static Message fromJSON(String json) {
        return gson.fromJson(json, Message.class);
    }

    /**
     * Getter for the sender
     *
     * @return The sender
     */
    public MessageSender getSender() {
        return this.sender;
    }

    /**
     * Getter for the channel
     *
     * @return The channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * @inheritDoc
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Getter for the placeHolderMessage
     *
     * @return The placeHolderMessage
     */
    public String getPlaceHolderMessage() {
        return this.placeHolderMessage;
    }

    /**
     * Setter for the placeHolderMessage
     *
     * @param placeHolderMessage The placeHolderMessage
     */
    public void setPlaceHolderMessage(String placeHolderMessage) {
        this.placeHolderMessage = placeHolderMessage;
    }

    /**
     * Getter for the placeHolders
     *
     * @return The placeHolders
     */
    public HashMap<String, String> getPlaceHolders() {
        return this.placeHolders;
    }

    /**
     * Setter for the placeHolders
     *
     * @param placeHolders The placeHolders
     */
    public void setPlaceHolders(HashMap<String, String> placeHolders) {
        this.placeHolders = placeHolders;
    }

    /**
     * Merge an existing placeHolders hashmap with the current one
     *
     * @param placeHolders The placeHolders to merge
     */
    public void mergePlaceHolders(HashMap<String, String> placeHolders) {
        this.placeHolders.putAll(placeHolders);
    }

    /**
     * Add a placeHolder to the placeHolders hashmap
     *
     * @param placeHolder The placeHolder to add
     */
    public void addPlaceHolder(String placeHolder, String value) {
        this.placeHolders.put(placeHolder, value);
    }

    /**
     * Applies the placeHolders to the placeHolderMessage and returns the result
     *
     * @return The placeHolderMessage with the placeHolders applied
     */
    public String applyPlaceHolders() {
        String message = this.placeHolderMessage;
        PlaceholderParser parser = sender.parsePlaceholders(message);
        for (String placeHolder : this.placeHolders.keySet()) {
            parser.parseString(placeHolder, this.placeHolders.get(placeHolder));
        }
        return parser.getResult();
    }

    /**
     * @inheritDoc
     */
    public long getTimestamp() {
        return this.timeStamp;
    }

    /**
     * Getter for the isRemote boolean
     *
     * @return The isRemote boolean
     */
    public boolean isRemote() {
        return this.isRemote;
    }

    /**
     * Setter for the isRemote boolean
     *
     * @param isRemote The isRemote boolean
     */
    public void setRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    /**
     * Getter for the isGlobal boolean
     *
     * @return The isGlobal boolean
     */
    public boolean isGlobal() {
        return this.isGlobal;
    }

    /**
     * Setter for the isGlobal boolean
     *
     * @param isGlobal The isGlobal boolean
     */
    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    /**
     * Converts the CommsMessage to a byte array
     *
     * @return The byte array
     */
    public byte[] toByteArray() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(gson.toJson(this));
        return out.toByteArray();
    }

    /**
     * Converts the CommsMessage to a JSON string
     *
     * @return The JSON string
     */
    public String toJSON() {
        return gson.toJson(this);
    }

    /**
     * Enum for the different types of messages that can be sent
     */
    public enum MessageType {
        PLAYER_ADVANCEMENT_FINISHED("tc:p_adv_fin"),
        PLAYER_DEATH("tc:p_death"),
        PLAYER_LOGIN("tc:p_login"),
        PLAYER_LOGOUT("tc:p_logout"),
        PLAYER_MESSAGE("tc:p_msg"),
        SERVER_STARTED("tc:s_start"),
        SERVER_STOPPED("tc:s_stop"),
        CUSTOM("tc:custom");

        private final String id;

        MessageType(String id) {
            this.id = id;
        }

        public static Set<String> getTypes() {
            return Arrays.stream(MessageType.values())
                    .map(MessageType::id)
                    .collect(Collectors.toSet());
        }

        public static MessageType fromId(String id) {
            return Arrays.stream(MessageType.values())
                    .filter(messageType -> messageType.id().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        public String id() {
            return this.id;
        }
    }
}
