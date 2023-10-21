package dev.neuralnexus.tatercomms.common.socket;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client class
 */
public class Client {
    private Socket socket;
    private final int port;
    private final String host;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Start the client
     */
//    public void start() {
//        try {
//            socket = new Socket(host, port);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            TaterComms.useLogger("Error starting Socket client on " + host + ":" + port);
//        }
//        Server.ClientHandler clientSock = new Server.ClientHandler(socket);
//        new Thread(clientSock).start();
//    }

    /**
     * Send a message to the server
     * @param message The message
     */
    public void sendMessage(CommsMessage message) {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket(host, port);
            }

            // Writing to server
//            DataOutputStream out
//                    = new DataOutputStream(
//                    socket.getOutputStream());
//
//            out.writeBytes(message.toJSON() + "\n");
//            out.flush();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message.toJSON());
            // TODO: Have this not-close the socket
            out.close();
            //

            //
            System.out.println("Sent " + message.toJSON() + " to " + host + ":" + port);
            //
        }
        catch (IOException e) {
            e.printStackTrace();
            socket = null;
            TaterComms.useLogger("Error sending message to " + host + ":" + port);
        }
    }
}
