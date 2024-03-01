/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.websocket.api;

import com.neovisionaries.ws.client.*;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterlib.Utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/** A class for handling WebSocket API requests. */
public class WebSocketClient {
    private final String host;
    private final int port;
    private final EncryptionHandler encryptionHandler;
    private final String identifier;
    private WebSocket ws;

    public WebSocketClient(String host, int port, String key, String identifier) {
        try {
            this.host = host;
            this.port = port;
            this.encryptionHandler = new EncryptionHandler(key);
            this.identifier = identifier;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    int reconnectAttempts = 0;
    final int maxReconnectAttempts = 20;

    /** Attempts to reconnect to the WebSocket server. */
    public void reconnect() {
        reconnectAttempts++;
        if (reconnectAttempts >= maxReconnectAttempts) {
            return;
        }
        Utils.runTaskLaterAsync(
                () -> {
                    try {
                        Switchboard.logger()
                                .info(
                                        "Reconnecting to WebSocket server... Attempt "
                                                + reconnectAttempts
                                                + " of "
                                                + maxReconnectAttempts);
                        ws.recreate();
                        Switchboard.logger().info("Reconnected to WebSocket server");
                    } catch (IOException e) {
                        this.reconnect();
                    }
                },
                600L);
    }

    /** Attempts to reconnect to the WebSocket server. */
    private void retryConnect() {
        reconnectAttempts++;
        if (reconnectAttempts >= maxReconnectAttempts) {
            return;
        }
        Utils.runTaskLaterAsync(
                () -> {
                    try {
                        Switchboard.logger()
                                .info(
                                        "Retrying WebSocket server connection... Attempt "
                                                + reconnectAttempts
                                                + " of "
                                                + maxReconnectAttempts);
                        ws =
                                new WebSocketFactory()
                                        .createSocket(
                                                "ws://"
                                                        + host
                                                        + ":"
                                                        + port
                                                        + "/websocket/"
                                                        + identifier);
                        ws.connect();
                        this.addListener();
                        Switchboard.logger().info("Connected to WebSocket server");
                    } catch (IOException | WebSocketException e) {
                        this.retryConnect();
                    }
                },
                600L);
    }

    /** Starts the WebSocket client. */
    public void start() {
        if (ws != null) {
            return;
        }
        try {
            ws =
                    new WebSocketFactory()
                            .createSocket("ws://" + host + ":" + port + "/websocket/" + identifier);
            ws.connect();
            this.addListener();
        } catch (IOException | WebSocketException e) {
            this.retryConnect();
        }
    }

    /** Stops the WebSocket client. */
    public void stop() {
        if (ws == null) {
            return;
        }
        ws.disconnect();
        ws = null;
    }

    /** Adds a listener to the WebSocket client. */
    private void addListener() {
        ws.addListener(
                new WebSocketAdapter() {
                    @Override
                    public void onConnected(WebSocket ws, Map<String, List<String>> headers) {
                        reconnectAttempts = 0;
                    }

                    @Override
                    public void onBinaryMessage(WebSocket ws, byte[] message) {
                        receiveMessage(message);
                    }

                    @Override
                    public void onConnectError(WebSocket ws, WebSocketException e) {
                        reconnect();
                    }

                    @Override
                    public void onDisconnected(
                            WebSocket ws,
                            WebSocketFrame serverCloseFrame,
                            WebSocketFrame clientCloseFrame,
                            boolean closedByServer) {
                        if (!closedByServer) {
                            reconnect();
                        }
                    }

                    @Override
                    public void onPingFrame(WebSocket ws, WebSocketFrame frame) {
                        ws.sendPong(frame.getPayload());
                    }
                });
    }

    /**
     * Sends a message to the WebSocket server.
     *
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        if (ws == null) {
            return;
        }
        if (message.isRemote()) {
            return;
        }
        try {
            ws.sendBinary(encryptionHandler.encrypt(message));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(byte[] content) {
        try {
            Message message = encryptionHandler.decrypt(content);
            message.setRemote(true);
            SwitchboardEvents.RECEIVE_MESSAGE.invoke(new ReceiveMessageEvent(message));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
