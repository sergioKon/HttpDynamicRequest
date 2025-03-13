package httpHandlers;


import converters.StreamDataParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;

import java.io.IOException;


public class OctetStreamHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.APPLICATION_OCTET_STREAM;
    }


    @Override
    public void proceed(HttpServletRequest request) throws IOException {

        byte[] clientData = request.getInputStream().readAllBytes();
        dataParser = new StreamDataParser();
        dataParser.saveToFile(clientData);
    }
}


