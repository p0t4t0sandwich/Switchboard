/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A wrapped message ready for transport
 *
 * @param version The version of the packet
 * @param source The source of the message
 * @param sink The sink of the message
 * @param sender The sender of the message
 * @param id The unique identifier of the message
 * @param type The type of the message
 * @param content The content of the message
 */
public record Packet(
        int version,
        String source,
        String sink,
        String sender,
        UUID id,
        String type,
        String content) {

    public byte[] toBytes() {
        ByteBuffer buffer =
                ByteBuffer.allocate(
                        1
                                + 4
                                + source.length()
                                + 4
                                + sink.length()
                                + 4
                                + sender.length()
                                + 16
                                + 4
                                + type.length()
                                + 4
                                + content.length());
        buffer.put((byte) version);
        buffer.putInt(source.length());
        buffer.put(source.getBytes());
        buffer.putInt(sink.length());
        buffer.put(sink.getBytes());
        buffer.putInt(sender.length());
        buffer.put(sender.getBytes());
        buffer.putLong(id.getMostSignificantBits());
        buffer.putLong(id.getLeastSignificantBits());
        buffer.putInt(type.length());
        buffer.put(type.getBytes());
        buffer.putInt(content.length());
        buffer.put(content.getBytes());
        return buffer.array();
    }

    public static Packet fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int version = buffer.get();
        byte[] sourceBytes = new byte[buffer.getInt()];
        buffer.get(sourceBytes);
        String source = new String(sourceBytes);
        byte[] sinkBytes = new byte[buffer.getInt()];
        buffer.get(sinkBytes);
        String sink = new String(sinkBytes);
        byte[] senderBytes = new byte[buffer.getInt()];
        buffer.get(senderBytes);
        String sender = new String(senderBytes);
        UUID message_id = new UUID(buffer.getLong(), buffer.getLong());
        byte[] messageTypeBytes = new byte[buffer.getInt()];
        buffer.get(messageTypeBytes);
        String messageType = new String(messageTypeBytes);
        byte[] contentBytes = new byte[buffer.getInt()];
        buffer.get(contentBytes);
        String content = new String(contentBytes);
        return new Packet(version, source, sink, sender, message_id, messageType, content);
    }
}
