package dev.neuralnexus.tatercomms.modules.socket.api;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.server.ProxyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketAPI {
    private Server socketServer = null;
    private Client socketClient = null;

    /** Start the socket client or server */
    public void startSocket() {
        if (TaterCommsConfigLoader.config().socket().primary()) {
            socketServer =
                    new Server(
                            TaterCommsConfigLoader.config().socket().port(),
                            TaterCommsConfigLoader.config().socket().secret());
            Utils.runTaskAsync(socketServer::start);
        } else {
            socketClient =
                    new Client(
                            TaterCommsConfigLoader.config().socket().host(),
                            TaterCommsConfigLoader.config().socket().port(),
                            TaterCommsConfigLoader.config().socket().secret());
            Utils.runTaskAsync(socketClient::start);
        }
    }

    /** Stop the socket client or server */
    public void stopSocket() {
        if (TaterCommsConfigLoader.config().socket().primary()) {
            socketServer.stop();
            socketServer = null;
        } else {
            socketClient.stop();
            socketClient = null;
        }
    }

    /**
     * Send a message to the server
     *
     * @param message The message
     */
    public void sendMessage(Message message) {
        if (TaterCommsConfigLoader.config().socket().primary()) {
            socketServer.sendMessageToAll(message);
        } else {
            socketClient.sendMessage(message);
        }
    }

    /**
     * Event handler for when a message is received
     *
     * @param event The event
     */
    public void onReceiveMessage(ReceiveMessageEvent event) {
        Message message = event.getMessage();

        // Relay player messages to socket clients
        if (socketServer != null
                && TaterCommsConfigLoader.config().socket().primary()
                && message.channel().equals(Message.MessageType.PLAYER_MESSAGE)) {
            socketServer.sendMessageToAll(message);
        }

        // Relay the message to socket server
        if (socketClient != null
                && message.getSender()
                        .server()
                        .name()
                        .equals(TaterAPIProvider.get().getServer().name())
                && !(TaterCommsConfigLoader.config().checkModule("proxy")
                        && (message.channel().equals(Message.MessageType.PLAYER_MESSAGE)
                                || message.channel().equals(Message.MessageType.PLAYER_LOGIN)
                                || message.channel().equals(Message.MessageType.PLAYER_LOGOUT)))) {
            socketClient.sendMessage(message);
        }
    }

    /** Server class */
    public static class Server {
        private static final HashMap<String, Socket> clients = new HashMap<>();
        private static ServerSocket server;
        private static int port;
        private static String secret;

        public Server(int port, String secret) {
            Server.port = port;
            Server.secret = secret;
        }

        /**
         * Really basic XOR obfuscation until some form of SSL is implemented
         *
         * @param message The message
         * @param secret The secret
         * @return The encrypted message
         */
        public static String XORMessage(String message, String secret) {
            StringBuilder encryptedMessage = new StringBuilder();
            for (int i = 0; i < message.length(); i++) {
                encryptedMessage.append(
                        (char) (message.charAt(i) ^ secret.charAt(i % secret.length())));
            }
            return encryptedMessage.toString();
        }

        /**
         * Add a client to the client hashmap
         *
         * @param serverName The server name
         * @param client The client
         */
        public static void addClient(String serverName, Socket client) {
            if ((TaterAPIProvider.serverType().isProxy()
                    && ((ProxyServer) TaterAPIProvider.get().getServer())
                            .servers().stream().anyMatch(s -> s.name().equals(serverName))
                    && !TaterAPIProvider.get().getServer().name().equals(serverName))) {
                clients.put(serverName, client);
            }
        }

        /**
         * Remove a client from the client hashmap
         *
         * @param serverName The server name
         */
        public static void removeClient(String serverName) {
            clients.remove(serverName);
        }

        /**
         * Checks if the client hashmap contains a client
         *
         * @param serverName The server name
         */
        public static boolean hasClient(String serverName) {
            return clients.containsKey(serverName);
        }

        /**
         * Is a client connected to the server
         *
         * @param serverName The server name
         */
        public static boolean isClientConnected(String serverName) {
            return clients.containsKey(serverName) && clients.get(serverName).isConnected();
        }

        /** Start the server */
        public void start() {
            try {
                TaterComms.logger().info("Starting Socket server on port " + port);
                server = new ServerSocket(port);
                server.setReuseAddress(true);

                while (true) {
                    Socket client = server.accept();
                    SocketHandler clientSock = new SocketHandler(client);
                    new Thread(clientSock).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                TaterComms.logger().info("Error running Socket server on port " + port);
            } finally {
                if (server != null) {
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /** Stop the server */
        public void stop() {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Send a message to every server
         *
         * @param message The message
         */
        public void sendMessageToAll(Message message) {
            for (String server : clients.keySet()) {
                if (!message.getSender().server().name().equals(server)) {
                    try {
                        Socket client = clients.get(server);

                        // Writing to client
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());

                        String json = message.toJSON();
                        String encryptedMessage = XORMessage(json, secret);
                        int length = encryptedMessage.length();
                        out.writeInt(length);
                        out.write(encryptedMessage.getBytes(), 0, length);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        removeClient(server);
                        TaterComms.logger().info("Error sending message to " + server);
                    }
                }
            }
        }

        /** ClientHandler class */
        public static class SocketHandler implements Runnable {
            private final Socket clientSocket;

            /**
             * Constructor for the ClientHandler class
             *
             * @param socket The socket
             */
            public SocketHandler(Socket socket) {
                this.clientSocket = socket;
            }

            /** Run the client handler and receive messages from the client */
            public void run() {
                while (!clientSocket.isClosed()) {
                    try {
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                        int dataLen = in.readInt();
                        byte[] data = new byte[dataLen];
                        in.readFully(data);

                        String dataString = new String(data);
                        String decryptedMessage = XORMessage(dataString, secret);

                        Message message = Message.fromJSON(decryptedMessage);
                        if (!isClientConnected(message.getSender().server().name())) {
                            addClient(message.getSender().server().name(), clientSocket);
                        }
                        message.setRemote(true);

                        if (message != null) {
                            // Fire the receive message event
                            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                    new ReceiveMessageEvent(message));
                        }

                        // Clear the input stream
                        in.skip(in.available());
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            clientSocket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        TaterComms.logger()
                                .info(
                                        "Error receiving message from "
                                                + clientSocket.getInetAddress().getHostAddress());
                    }
                }
            }
        }
    }

    /** Client class */
    public static class Client {
        private static boolean STARTED = true;
        private final int port;
        private final String host;
        private final String secret;
        private Socket socket;

        public Client(String host, int port, String secret) {
            this.host = host;
            this.port = port;
            this.secret = secret;
        }

        /** Start the client */
        public void start() {
            while (STARTED) {
                try {
                    if (socket == null || socket.isClosed()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    int dataLen = in.readInt();
                    byte[] data = new byte[dataLen];
                    in.readFully(data);

                    String dataString = new String(data);
                    String decryptedMessage = Server.XORMessage(dataString, secret);

                    Message message = Message.fromJSON(decryptedMessage);
                    if (message != null) {
                        // Fire the receive message event
                        TaterCommsEvents.RECEIVE_MESSAGE.invoke(new ReceiveMessageEvent(message));
                    }

                    // Clear the input stream
                    in.skip(in.available());
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    TaterComms.logger()
                            .info(
                                    "Error receiving message from "
                                            + socket.getInetAddress().getHostAddress());
                }
            }
        }

        /** Stop the client */
        public void stop() {
            STARTED = false;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Send a message to the server
         *
         * @param message The message
         */
        public void sendMessage(Message message) {
            try {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(host, port);
                }

                // Writing to server
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String json = message.toJSON();
                String encryptedMessage = Server.XORMessage(json, secret);
                int length = encryptedMessage.length();
                out.writeInt(length);
                out.write(encryptedMessage.getBytes(), 0, length);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                socket = null;
                TaterComms.logger().info("Error sending message to " + host + ":" + port);
            }
        }
    }
}
