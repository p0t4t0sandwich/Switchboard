/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.websocket.api;

import dev.neuralnexus.switchboard.api.message.Message;

/** A class for handling WebSocket API requests. */
public class WebSocketAPI {
    private WebSocketServer server = null;
    private WebSocketClient client = null;

    /** Starts the WebSocket server or client. */
    public void startWebSocket(String host, int port, String key, String mode, String identifier) {
        if (mode.equals("server")) {
            server = new WebSocketServer(host, port, key);
            server.start();
        } else if (mode.equals("client")) {
            client = new WebSocketClient(host, port, key, identifier);
            client.start();
        }
    }

    /** Stops the WebSocket server or client. */
    public void stopWebSocket() {
        if (server != null) {
            server.stop();
        } else if (client != null) {
            client.stop();
        }
    }

    /** Sends a message to the WebSocket server or client. */
    public void sendMessage(Message message) {
        if (server != null) {
            server.sendMessage(message);
        } else if (client != null) {
            client.sendMessage(message);
        }
    }
}
