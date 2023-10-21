package dev.neuralnexus.tatercomms.common.socket;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Client class
public class Client {
    private Socket socket = null;
    private final int port;
    private final String host;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage(CommsMessage message) {
        try {
//            if (socket == null) {
//                socket = new Socket(host, port);
//            }
            socket = new Socket(host, port);

            // Writing to server
            DataOutputStream out
                    = new DataOutputStream(
                    socket.getOutputStream());

            out.writeUTF(message.toJSON());
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            TaterComms.useLogger("Error sending message to " + host + ":" + port);
        }
    }
}
