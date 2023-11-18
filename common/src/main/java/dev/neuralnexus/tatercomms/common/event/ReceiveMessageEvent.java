package dev.neuralnexus.tatercomms.common.event;

import dev.neuralnexus.tatercomms.common.event.api.Message;

/**
 * Receive message event.
 */
public class ReceiveMessageEvent {
    private final Message message;

    public ReceiveMessageEvent(Message message) {
        this.message = message;
    }

    /**
     * Get the message.
     * @return The message.
     */
    public Message getMessage() {
        return message;
    }
}
