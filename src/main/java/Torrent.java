import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.util.Map;

public class Torrent {
    private String trackerUrl;
    private long length;

    public Torrent(byte[] torrentMetaInfoBytes) {
        Bencode bencode = new Bencode();

        Map<String, Object> metaInfo = bencode.decode(torrentMetaInfoBytes, Type.DICTIONARY);
        Map<String, Object> infoDict = (Map<String, Object>) metaInfo.get("info");

        this.trackerUrl = (String) metaInfo.get("announce");
        this.length = (long) infoDict.get("length");
    }

    @Override
    public String toString() {
        return "Tracker URL: " + this.trackerUrl + System.lineSeparator() + "Length: " + this.length;
    }
}
