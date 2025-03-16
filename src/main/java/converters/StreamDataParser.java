package converters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StreamDataParser extends DataParser {
    @Override
    public void saveToFile(byte[] bytes) throws IOException {
        bufferSize=bytes.length;
        extension=".bin";
        Path root = Paths.get("");// .toAbsolutePath().toFile().getName();
        rootLocation = root.toAbsolutePath().toFile().getName();
        super.saveToFile(bytes);
    }
}
