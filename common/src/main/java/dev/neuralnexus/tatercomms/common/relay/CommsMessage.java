package dev.neuralnexus.tatercomms.common.relay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.neuralnexus.tatercomms.common.listeners.player.CommonPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.CommonServerListener;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @param args The arguments
     */
    public static void parseMessageChannel(Object[] args) {
        String channel = args[0].toString();
        CommsMessage message;
        byte[] data = (byte[]) args[1];
        try {
            message = CommsMessage.fromByteArray(data);
        } catch (Exception e) {
            // TODO: Make this less jank
            message = gson.fromJson(new String(Arrays.copyOfRange(data, 7, data.length)), CommsMessage.class);
        }

        MessageType messageType = MessageType.fromIdentifier(channel);
        switch (messageType) {
            case PLAYER_ADVANCEMENT_FINISHED:
                CommonPlayerListener.onPlayerAdvancementFinished(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_DEATH:
                CommonPlayerListener.onPlayerDeath(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_LOGIN:
                CommonPlayerListener.onPlayerLogin(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_LOGOUT:
                CommonPlayerListener.onPlayerLogout(new Object[]{message.getSender(), message.getMessage()});
                break;
            case PLAYER_MESSAGE:
                CommonPlayerListener.onPlayerMessage(new Object[]{message.getSender(), message.getMessage()});
                break;
            case SERVER_STARTED:
                CommonServerListener.onServerStarted(new Object[]{message.getSender().getServerName()});
                break;
            case SERVER_STOPPED:
                CommonServerListener.onServerStopped(new Object[]{message.getSender().getServerName()});
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
        SERVER_STOPPED("tc:s_stop");

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

    public byte[] toByteArray() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(gson.toJson(this));
        return out.toByteArray();
    }

    public static CommsMessage fromByteArray(byte[] data) {
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        String json = in.readUTF();
        return gson.fromJson(json, CommsMessage.class);
    }
}
