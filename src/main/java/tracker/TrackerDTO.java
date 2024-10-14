package tracker;

import lombok.Builder;

import java.lang.reflect.Field;

@Builder
public class TrackerDTO {
    // The 20 byte sha1 hash of the bencoded form of the info value from the metainfo file. This value will almost certainly have to be escaped.
    private String info_hash;

    // A string of length 20 which this downloader uses as its id.
    // Each downloader generates its own id at random at the start of a new download. This value will also almost certainly have to be escaped.
    private String peer_id;

    // The port number this peer is listening on.
    // Common behavior is for a downloader to try to listen on port 6881 and if that port is taken try 6882, then 6883, etc. and give up after 6889.
    private String port;

    // The total amount uploaded so far, encoded in base ten ascii.
    private String uploaded;

    // The total amount downloaded so far, encoded in base ten ascii.
    private String downloaded;

    // The number of bytes this peer still has to download, encoded in base ten ascii.
    // Note that this can't be computed from downloaded and the file length since it might be a resume,
    // and there's a chance that some of the downloaded data failed an integrity check and had to be re-downloaded.
    private String left;

    // Whether the peer list should use the compact representation
    private String compact;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(Field field: this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                result.append(field.getName())
                        .append("=")
                        .append(field.get(this))
                        .append("&");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }
}
