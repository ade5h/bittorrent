import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Torrent {
    private String trackerUrl;
    private long length;
    private String infoHash;

    public Torrent(byte[] torrentMetaInfoBytes) throws NoSuchAlgorithmException {
        Bencode bencode = new Bencode(true);

        Map<String, Object> metaInfo = bencode.decode(torrentMetaInfoBytes, Type.DICTIONARY);
        Map<String, Object> infoDict = (Map<String, Object>) metaInfo.get("info");

        byte[] bencodedByteArray = bencode.encode(infoDict);

        this.infoHash = hashTheByteArray(bencodedByteArray);
        this.trackerUrl = StandardCharsets.UTF_8.decode((ByteBuffer) metaInfo.get("announce")).toString();
        this.length = (long) infoDict.get("length");
    }

    @Override
    public String toString() {
        return "Tracker URL: " + this.trackerUrl +
                System.lineSeparator() +
                "Length: " + this.length +
                System.lineSeparator() +
                "Info Hash: " + this.infoHash;
    }

    private String hashTheByteArray(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(bytes);
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
