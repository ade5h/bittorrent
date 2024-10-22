package handshake;

import torrent.Torrent;

import java.io.IOException;

public class PeerHandshakeService {
    public static PeerHandshakeDTO connectWithPeer(String ipAddress, int port, Torrent torrent) throws IOException {
        TCPClient client = new TCPClient(ipAddress, port);
        PeerHandshakeDTO peerHandshakeDTO = new PeerHandshakeDTO(new byte[20], torrent.getInfoHash());

        byte[] response = client.sendAndReceive(peerHandshakeDTO.getBytes());

        return new PeerHandshakeDTO(response);
    }
}
