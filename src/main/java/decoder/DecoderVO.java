package decoder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DecoderVO {
    private Object decodedObj;
    private int nextIndex;
}