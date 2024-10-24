import com.google.gson.Gson;
import decoder.BencodedDecorder;
import handshake.PeerHandshakeDTO;
import handshake.PeerHandshakeService;
import torrent.Torrent;
import tracker.TrackerService;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        String command = args[0];
        if("decode".equals(command)) {
            String bencodedValue = args[1];
            Object decoded;
            try {
                decoded = BencodedDecorder.decodeBencode(bencodedValue, 0).getDecodedObj();
            } catch(RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(gson.toJson(decoded));
        }
        else if("info".equals(command)) {
            String torrentFileName = args[1];

            File torrentFile = new File(torrentFileName);
            byte[] bytes = Files.readAllBytes(torrentFile.toPath());

            Torrent torrent = new Torrent(bytes);
            System.out.println(torrent);
        }
        else if("peers".equals(command)) {
            String torrentFileName = args[1];

            File torrentFile = new File(torrentFileName);
            byte[] bytes = Files.readAllBytes(torrentFile.toPath());

            Torrent torrent = new Torrent(bytes);

            List<String> peers = new TrackerService().getPeers(torrent);

            for(String peer: peers) {
                System.out.println(peer);
            }
        }
        else if("handshake".equals(command)) {
            String torrentFileName = args[1];
            String ipAndPort = args[2];

            String[] parts = ipAndPort.split(":");

            String peerIp = parts[0];
            int peerPort = Integer.parseInt(parts[1]);

            File torrentFile = new File(torrentFileName);
            byte[] bytes = Files.readAllBytes(torrentFile.toPath());

            Torrent torrent = new Torrent(bytes);

            PeerHandshakeDTO response = PeerHandshakeService.connectWithPeer(peerIp, peerPort, torrent);

            System.out.println("Peer ID: " + Torrent.byteArrayToHexaDecimal(response.getPeerId()));
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }
}
