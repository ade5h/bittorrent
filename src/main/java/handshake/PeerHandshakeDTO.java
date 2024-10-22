package handshake;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class PeerHandshakeDTO {
    byte[] infoHash;
    byte[] peerId;

    public PeerHandshakeDTO(byte[] peerId, byte[] infoHash) {
        this.peerId = peerId;
        this.infoHash = infoHash;
    }

    public PeerHandshakeDTO(byte[] byteArray) {
        this.infoHash = Arrays.copyOfRange(byteArray, 28, 48);
        this.peerId = Arrays.copyOfRange(byteArray, 48, 68);
    }

    public byte[] getBytes() {
        ArrayList<Byte> handshakeBytes = new ArrayList<>();
        Byte[] infoHashByteObj = ArrayUtils.toObject(infoHash);
        Byte[] peerIdByteObj = ArrayUtils.toObject(peerId);
        Byte[] protocolName = ArrayUtils.toObject("BitTorrent protocol".getBytes(StandardCharsets.UTF_8));
        Byte[] reservedBytes = new Byte[8];
        Arrays.fill(reservedBytes, (byte) 0);

        handshakeBytes.add((byte) 19);
        handshakeBytes.addAll(Arrays.asList(protocolName));
        handshakeBytes.addAll(Arrays.asList(reservedBytes));
        handshakeBytes.addAll(Arrays.asList(infoHashByteObj));
        handshakeBytes.addAll(Arrays.asList(peerIdByteObj));

        Byte[] handshakeBytesArray = handshakeBytes.toArray(new Byte[0]);

        return ArrayUtils.toPrimitive(handshakeBytesArray);
    }

    public byte[] getInfoHash() {
        return infoHash;
    }

    public byte[] getPeerId() {
        return peerId;
    }
}
