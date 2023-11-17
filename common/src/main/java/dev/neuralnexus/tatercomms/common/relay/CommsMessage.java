package dev.neuralnexus.tatercomms.common.relay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.neuralnexus.tatercomms.common.listeners.player.TaterCommsPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.CommonServerListener;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.common.player.Player;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for relaying messages between the server and Discord
 */
public class CommsMessage {
    private final CommsSender sender;
    private final String channel;
    private final String message;
    private String placeHolderMessage = "";
    private HashMap<String, String> placeHolders = new HashMap<>();
    private final long timeStamp;
    private boolean isRemote = false;
    private boolean isGlobal = false;

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(CommsSender sender, String channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
        this.placeHolderMessage = placeHolderMessage;
        this.placeHolders = placeHolders;
        placeHolders.put("message", message);
        this.timeStamp = System.currentTimeMillis(); // ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(CommsSender sender, MessageType channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this(sender, channel.getIdentifier(), message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     * @param serverName The server name
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(String serverName, String channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this(new CommsSender(serverName), channel, message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     * @param serverName The server name
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(String serverName, MessageType channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this(new CommsSender(serverName), channel.getIdentifier(), message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(Player sender, String channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this(new CommsSender(sender), channel, message, placeHolderMessage, placeHolders);
    }

    /**
     * Constructor for the CommsMessage class
     * @param sender The sender
     * @param channel The channel
     * @param message The message
     */
    public CommsMessage(Player sender, MessageType channel, String message, String placeHolderMessage, HashMap<String, String> placeHolders) {
        this(new CommsSender(sender), channel.getIdentifier(), message, placeHolderMessage, placeHolders);
    }

    /**
     * Getter for the sender
     * @return The sender
     */
    public CommsSender getSender() {
        return this.sender;
    }

    /**
     * Getter for the channel
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
     * @return The placeHolderMessage
     */
    public String getPlaceHolderMessage() {
        return this.placeHolderMessage;
    }

    /**
     * Setter for the placeHolderMessage
     * @param placeHolderMessage The placeHolderMessage
     */
    public void setPlaceHolderMessage(String placeHolderMessage) {
        this.placeHolderMessage = placeHolderMessage;
    }

    /**
     * Getter for the placeHolders
     * @return The placeHolders
     */
    public HashMap<String, String> getPlaceHolders() {
        return this.placeHolders;
    }

    /**
     * Setter for the placeHolders
     * @param placeHolders The placeHolders
     */
    public void setPlaceHolders(HashMap<String, String> placeHolders) {
        this.placeHolders = placeHolders;
    }

    /**
     * Merge an existing placeHolders hashmap with the current one
     * @param placeHolders The placeHolders to merge
     */
    public void mergePlaceHolders(HashMap<String, String> placeHolders) {
        this.placeHolders.putAll(placeHolders);
    }

    /**
     * Add a placeHolder to the placeHolders hashmap
     * @param placeHolder The placeHolder to add
     */
    public void addPlaceHolder(String placeHolder, String value) {
        this.placeHolders.put(placeHolder, value);
    }

    /**
     * Applies the placeHolders to the placeHolderMessage and returns the result
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
     * @return The isRemote boolean
     */
    public boolean isRemote() {
        return this.isRemote;
    }

    /**
     * Setter for the isRemote boolean
     * @param isRemote The isRemote boolean
     */
    public void setRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    /**
     * Getter for the isGlobal boolean
     * @return The isGlobal boolean
     */
    public boolean isGlobal() {
        return this.isGlobal;
    }

    /**
     * Setter for the isGlobal boolean
     * @param isGlobal The isGlobal boolean
     */
    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    static Gson gson = new GsonBuilder().create();

    /**
     * Message channel parser
     * @param args The arguments
     */
    public static void parseMessageChannel(Object[] args) {
        CommsMessage message;
        byte[] data = (byte[]) args[1];
        try {
            message = CommsMessage.fromByteArray(data);
        } catch (Exception e) {
            // TODO: Make this less jank
            try {
                // Forge Support
                message = gson.fromJson(new String(Arrays.copyOfRange(data, 7, data.length)), CommsMessage.class);
            } catch (Exception ex) {
                // Fabric Support
                message = gson.fromJson(new String(Arrays.copyOfRange(data, 4, data.length)), CommsMessage.class);
            }
        }

        MessageType messageType = MessageType.fromIdentifier(message.getChannel());
        switch (messageType) {
            case PLAYER_ADVANCEMENT_FINISHED:
                TaterCommsPlayerListener.onPlayerAdvancementFinished(new CommsEvents.CommsPlayerAdvancementFinishedEvent(message));
                break;
            case PLAYER_DEATH:
                TaterCommsPlayerListener.onPlayerDeath(new CommsEvents.CommsPlayerDeathEvent(message));
                break;
            case PLAYER_LOGIN:
                TaterCommsPlayerListener.onPlayerLogin(new CommsEvents.CommsPlayerLoginEvent(message));
                break;
            case PLAYER_LOGOUT:
                TaterCommsPlayerListener.onPlayerLogout(new CommsEvents.CommsPlayerLogoutEvent(message));
                break;
            case PLAYER_MESSAGE:
                TaterCommsPlayerListener.onPlayerMessage(new CommsEvents.CommsPlayerMessageEvent(message));
                break;
            case SERVER_STARTED:
                CommonServerListener.onServerStarted(new Object[]{message.getSender().getServerName()});
                break;
            case SERVER_STOPPED:
                CommonServerListener.onServerStopped(new Object[]{message.getSender().getServerName()});
                break;
            case CUSTOM:
//                CommonServerListener.onCustomMessage(new Object[]{message.getSender().getServerName(), message.getMessage()});
                break;
        }
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

        private final String identifier;

        MessageType(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public static Set<String> getTypes() {
            return Arrays.stream(MessageType.values()).map(MessageType::getIdentifier).collect(Collectors.toSet());
        }

        public static MessageType fromIdentifier(String identifier) {
            return Arrays.stream(MessageType.values()).filter(messageType -> messageType.getIdentifier().equals(identifier)).findFirst().orElse(null);
        }
    }

    /**
     * Converts the CommsMessage to a byte array
     * @return The byte array
     */
    public byte[] toByteArray() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(gson.toJson(this));
        return out.toByteArray();
    }

    /**
     * Creates a CommsMessage from a byte array
     * @param data The byte array
     * @return The CommsMessage
     */
    public static CommsMessage fromByteArray(byte[] data) {
        try {
            try {
                ByteArrayDataInput in = ByteStreams.newDataInput(data);
                String json = in.readUTF();
                return gson.fromJson(json, CommsMessage.class);
            } catch (Exception e) {
                return gson.fromJson(new String(data), CommsMessage.class);
            }
        } catch (Exception e) {
            // TODO: Make this less jank
            try {
                // Fabric Support
                return gson.fromJson(new String(Arrays.copyOfRange(data, 4, data.length)), CommsMessage.class);
            } catch (Exception ex) {
                // Forge Support
                return gson.fromJson(new String(Arrays.copyOfRange(data, 7, data.length)), CommsMessage.class);
            }
        }
    }

    /**
     * Creates a CommsMessage from a JSON string
     * @param json The JSON string
     * @return The CommsMessage
     */
    public static CommsMessage fromJSON(String json) {
        return gson.fromJson(json, CommsMessage.class);
    }

    /**
     * Converts the CommsMessage to a JSON string
     * @return The JSON string
     */
    public String toJSON() {
        return gson.toJson(this);
    }
}
