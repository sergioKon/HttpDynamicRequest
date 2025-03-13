package converters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



public  class DataParser  implements  Closeable {
   private static final Logger logger = LoggerFactory.getLogger(DataParser.class);
   protected String extension;
   protected String rootLocation;
   protected  String name;
   protected  int bufferSize= 8 * 1024;
   private IOException exception= null;

    private String getFullPath() {
       return  rootLocation + name + extension;
    }

    public void  saveToFile(byte[] bytes ) {
        try (PrintStream printStream= new PrintStream(rootLocation +extension)){
            printStream.write(bytes);
        } catch (IOException e) {
            this.exception = e;
        }
    }

   public  void writeBinary(InputStream inputStream) {
      java.nio.file.Path filePath = Paths.get(rootLocation +extension);

      // Open a channel in write mode on your file.
      try (WritableByteChannel channel = Files.newByteChannel(filePath, StandardOpenOption.CREATE)) {
         // Allocate a new buffer.
            ByteBuffer buf = ByteBuffer.allocate(bufferSize * 10 );
            buf.put(new byte[inputStream.available()] );
            channel.write(buf);  // Write your buffer's data.
         } catch (IOException e) {
          this.exception = e;
        }
      }

    @Override
    public void close() throws IOException {
        if(exception != null) {
            throw new IOException(exception);
        }
        else {
            logger.debug("{} finished successfully" , this.getClass().getName());
        }
    }
}

