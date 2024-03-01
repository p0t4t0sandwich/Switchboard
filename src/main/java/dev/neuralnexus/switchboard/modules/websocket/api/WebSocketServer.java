/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.websocket.api;

import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;

import io.javalin.Javalin;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/** A class for handling WebSocket API requests. */
public class WebSocketServer {
    private final String host;
    private final int port;
    private final EncryptionHandler encryptionHandler;
    private final Map<String, WsConnectContext> clients = new HashMap<>();
    private Javalin app = null;

    public WebSocketServer(String host, int port, String key) {
        try {
            this.host = host;
            this.port = port;
            this.encryptionHandler = new EncryptionHandler(key);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /** Starts the WebSocket server. */
    public void start() {
        if (app != null) {
            return;
        }
        app = Javalin.create();
        app.ws(
                "/websocket/{path}",
                ws -> {
                    ws.onConnect(this::addClient);
                    ws.onClose(this::removeClient);
                    ws.onBinaryMessage(this::receiveMessage);
                });
        app.start(host, port);
    }

    /** Stops the WebSocket server. */
    public void stop() {
        if (app == null) {
            return;
        }
        app.stop();
        app = null;
    }

    /**
     * Receives a message from a client.
     *
     * @param ctx The context to use.
     */
    public void receiveMessage(WsBinaryMessageContext ctx) {
        try {
            Message message = encryptionHandler.decrypt(ctx.data());
            message.setRemote(true);
            SwitchboardEvents.RECEIVE_MESSAGE.invoke(new ReceiveMessageEvent(message));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a client to the list of connected clients.
     *
     * @param ctx The context to use.
     */
    public void addClient(WsConnectContext ctx) {
        ctx.enableAutomaticPings();
        clients.put(ctx.pathParam("path"), ctx);
    }

    /**
     * Removes a client from the list of connected clients.
     *
     * @param ctx The context to use.
     */
    public void removeClient(WsCloseContext ctx) {
        clients.remove(ctx.pathParam("path"));
    }

    /**
     * Sends a message to a specific client.
     *
     * @param message The message to send.
     */
    public void sendMessage(Message message) {
        if (message.isRemote()) {
            return;
        }
        clients.values().stream()
                .filter(ctx -> ctx.session.isOpen())
                .forEach(
                        session -> {
                            try {
                                session.send(ByteBuffer.wrap(encryptionHandler.encrypt(message)));
                            } catch (GeneralSecurityException e) {
                                e.printStackTrace();
                            }
                        });
    }
}
