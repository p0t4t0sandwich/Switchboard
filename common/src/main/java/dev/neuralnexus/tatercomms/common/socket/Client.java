package dev.neuralnexus.tatercomms.common.socket;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static dev.neuralnexus.tatercomms.common.socket.Server.XORMessage;

/**
 * Client class
 */
public class Client {
    private Socket socket;
    private final int port;
    private final String host;
    private final String secret;

    public Client(String host, int port, String secret) {
        this.host = host;
        this.port = port;
        this.secret = secret;
    }

    /**
     * Start the client
     */
    public void start() {
        while (true) {
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
                String decryptedMessage = XORMessage(dataString, secret);

                CommsMessage message = CommsMessage.fromJSON(decryptedMessage);
                if (message != null) {
                    CommsMessage.parseMessageChannel(new Object[]{"", message.toByteArray()});
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
                TaterComms.getLogger().info("Error receiving message from " + socket.getInetAddress().getHostAddress());
            }
        }
    }

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
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String json = message.toJSON();
            String encryptedMessage = XORMessage(json, secret);
            int length = encryptedMessage.length();
            out.writeInt(length);
            out.write(encryptedMessage.getBytes(), 0, length);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            socket = null;
            TaterComms.getLogger().info("Error sending message to " + host + ":" + port);
        }
    }
}
