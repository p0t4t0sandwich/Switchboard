package dev.neuralnexus.tatercomms.common.relay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.neuralnexus.tatercomms.common.listeners.player.CommonPlayerListener;
import dev.neuralnexus.tatercomms.common.listeners.server.CommonServerListener;
import dev.neuralnexus.taterlib.lib.gson.Gson;
import dev.neuralnexus.taterlib.lib.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashSet;

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
        CommsMessage message = CommsMessage.fromByteArray(data);
        switch (channel) {
            case "tatercomms:player_advancement_finished":
                CommonPlayerListener.onPlayerAdvancementFinished(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_death":
                CommonPlayerListener.onPlayerDeath(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_login":
                CommonPlayerListener.onPlayerLogin(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_logout":
                CommonPlayerListener.onPlayerLogout(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:player_message":
                CommonPlayerListener.onPlayerMessage(new Object[]{message.getSender(), message.getMessage()});
                break;
            case "tatercomms:server_started":
                CommonServerListener.onServerStarted(new Object[]{message.getSender().getServerName()});
                break;
            case "tatercomms:server_stopped":
                CommonServerListener.onServerStopped(new Object[]{message.getSender().getServerName()});
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
        PLAYER_MESSAGE("tatercomms:player_message"),
        SERVER_STARTED("tatercomms:server_started"),
        SERVER_STOPPED("tatercomms:server_stopped");

        private final String identifier;

        MessageType(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public static HashSet<MessageType> getTypes() {
            return new HashSet<>(Arrays.asList(MessageType.values()));
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
