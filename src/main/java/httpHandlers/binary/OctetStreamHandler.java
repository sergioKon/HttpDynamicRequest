package httpHandlers.binary;


import com.sun.net.httpserver.HttpExchange;
import converters.StreamDataParser;
import httpHandlers.HTTPAbstractHandler;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;
import java.io.InputStream;


public class OctetStreamHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
          super.mediaType= CustomMediaType.OCTET_STREAM;
    }


    @Override
    public void proceed(HttpExchange exchange) {
        byte[] clientData;
        try(InputStream inputStream = exchange.getRequestBody() ) {
             clientData= inputStream.readAllBytes();
             dataParser = new StreamDataParser();
             dataParser.saveToFile(clientData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


