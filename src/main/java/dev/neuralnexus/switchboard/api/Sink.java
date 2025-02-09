package dev.neuralnexus.switchboard.api;

/**
 * A destination for messages
 */
public interface Sink {
    /**
     * Get the name of the Sink
     *
     * @return The name of the Sink
     */
    String name();

    /**
     * Send a message to the Sink
     *
     * @param message The message to send
     */
    void send(Message message);
}
