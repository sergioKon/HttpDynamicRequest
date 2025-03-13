package httpHandlers.binary;


import converters.StreamDataParser;
import httpHandlers.HTTPAbstractHandler;
import jakarta.servlet.http.HttpServletRequest;
import rest.mainServlet.CustomMediaType;


import java.io.IOException;


public class OctetStreamHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
          super.mediaType= CustomMediaType.OCTET_STREAM;
    }


    @Override
    public void proceed(HttpServletRequest request) throws IOException {

        byte[] clientData = request.getInputStream().readAllBytes();
        dataParser = new StreamDataParser();
        dataParser.saveToFile(clientData);
    }
}


