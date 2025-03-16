package httpHandlers.binary;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import converters.GifParser;
import converters.JSonParser;
import converters.PgnParser;
import converters.XMLParser;
import httpHandlers.HTTPAbstractHandler;
import rest.mainServlet.CustomMediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


public class MultipartHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType = CustomMediaType.MULTIPART_FORM_DATA;
    }

    @Override
    public void proceed(HttpExchange exchange)  {
        Headers headers = exchange.getRequestHeaders();
        String contentType = headers.getFirst("Content-Type");
        if (contentType.startsWith("multipart/")) {
            String boundary = getBoundary(contentType);
            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().collect(Collectors.joining("\n"));
            String[] parts = requestBody.split("--" + boundary);
            for (String part : parts) {
                CustomMediaType mediaType = CustomMediaType.valueOf(part);
                switch (mediaType) {
                    case APPLICATION_XML:
                        dataParser = new XMLParser();
                        break;
                    case APPLICATION_JSON:
                        dataParser = new JSonParser();
                        break;
                    case IMAGE_GIF:
                        dataParser = new GifParser();

                    case IMAGE_JPEG:
                        dataParser = new GifParser();

                    case IMAGE_PNG:
                        dataParser = new PgnParser();
                }
                try {
                    dataParser.saveToFile(part.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String getBoundary(String contentType) {
        String[] parts = contentType.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("boundary=")) {
                return part.split("=")[1].trim();
            }
        }
        return null;
    }
}






