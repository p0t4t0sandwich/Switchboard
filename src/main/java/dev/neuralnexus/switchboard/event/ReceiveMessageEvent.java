package dev.neuralnexus.switchboard.event;

import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.taterlib.event.Event;

/** Receive message event. */
public class ReceiveMessageEvent implements Event {
    private final Message message;

    public ReceiveMessageEvent(Message message) {
        if (message.isRemote()) {
            message.setPlaceHolderMessage(SwitchboardConfigLoader.config().formatting().remote());
        } else if (message.isGlobal()) {
            message.setPlaceHolderMessage(SwitchboardConfigLoader.config().formatting().global());
        }
        this.message = message;
    }

    /**
     * Get the message.
     *
     * @return The message.
     */
    public Message getMessage() {
        return message;
    }
}
