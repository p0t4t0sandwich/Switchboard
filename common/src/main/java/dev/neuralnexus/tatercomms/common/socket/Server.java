package dev.neuralnexus.tatercomms.common.socket;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Server class
 */
public class Server {
    private static ServerSocket server;
    private static int port;
    private static final HashMap<String, Socket> clients = new HashMap<>();

    public Server(int port) {
        Server.port = port;
    }

    /**
     * Start the server
     */
    public void start() {
        try {
            TaterComms.useLogger("Starting Socket server on port " + port);
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            TaterComms.useLogger("Error running Socket server on port " + port);
        } finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Stop the server
     */
    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to every server
     * @param message The message
     */
    public static void sendMessageToAll(CommsMessage message) {
        for (String server : clients.keySet()) {
            try {
                Socket client = clients.get(server);

                // Writing to server
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                out.writeUTF(message.toJSON() + "\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                TaterComms.useLogger("Error sending message to " + server);
            }
        }
    }

    /**
     * Add a client to the client hashmap
     * @param serverName The server name
     * @param client The client
     */
    public static void addClient(String serverName, Socket client) {
        clients.put(serverName, client);
    }

    /**
     * Remove a client from the client hashmap
     * @param serverName The server name
     */
    public static void removeClient(String serverName) {
        clients.remove(serverName);
    }

    /**
     * Checks if the client hashmap contains a client
     * @param serverName The server name
     */
    public static boolean hasClient(String serverName) {
        return clients.containsKey(serverName);
    }

    /**
     * ClientHandler class
     */
    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        /**
         * Constructor for the ClientHandler class
         * @param socket The socket
         */
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        /**
         * Receive a message from the client
         */
        public void receiveMessage() {
            try {
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                int dataLen = in.readInt();
                byte[] data = new byte[dataLen];
                in.readFully(data);
                CommsMessage message = CommsMessage.fromJSON(new String(data));
                if (message != null) {
                    Server.addClient(message.getSender().getServerName(), clientSocket);
                    CommsMessage.parseMessageChannel(new Object[]{"", message.toByteArray()});
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                TaterComms.useLogger("Error receiving message from " + clientSocket.getInetAddress().getHostAddress());
            }
        }

        public void run() {
            while (!clientSocket.isClosed()) {
                receiveMessage();
            }
        }
    }
}
