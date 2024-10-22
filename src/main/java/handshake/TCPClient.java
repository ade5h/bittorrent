package handshake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    private String host;
    private int port;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public byte[] sendAndReceive(byte[] message) throws IOException {
        Socket socket = null;
        DataOutputStream out = null;
        DataInputStream in = null;

        try {
            // Create socket connection
            socket = new Socket(host, port);

            // Create reader and writer for communication
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // Send message
            out.write(message);

            // Read response
            byte[] response = new byte[68];
            in.readFully(response);

            return response;
        } finally {
            // Clean up resources
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        }

    }
}
