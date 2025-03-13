package common.http.request;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MimiTypeTest {
    @Test
    public void readTypeFromFile()  {
      //  InputStream targetStream = new FileInputStream("C:/NcatPortable/README.md");
        Path filePath = Paths.get("C:/Eliran/WhatsApp Image 2024-07-17 at 15.54.43 (1).jpeg"); // Replace with your file path

        try {
            String fileType = Files.probeContentType(filePath);
            System.out.println("File type: " + fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (InputStream inputStream = new FileInputStream("C:/NcatPortable/README.md")) { // Replace with your file path
            String fileType = URLConnection.guessContentTypeFromStream(inputStream);
            System.out.println("File type: " + (fileType != null ? fileType : "unknown"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
