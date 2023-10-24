package dev.neuralnexus.tatercomms.common.socket;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
                CommsMessage message = CommsMessage.fromJSON(new String(data));
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
                TaterComms.useLogger("Error receiving message from " + socket.getInetAddress().getHostAddress());
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
            int length = json.length();
            out.writeInt(length);
            out.write(json.getBytes(), 0, length);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            socket = null;
            TaterComms.useLogger("Error sending message to " + host + ":" + port);
        }
    }
}
