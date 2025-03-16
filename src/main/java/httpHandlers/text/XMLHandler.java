package httpHandlers.text;

import com.sun.net.httpserver.HttpExchange;
import httpHandlers.HTTPAbstractHandler;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;


/**
 *
 */
public class XMLHandler extends HTTPAbstractHandler {

    @Override
    public void proceed(HttpExchange exchange) throws IOException {
        byte[] xmlStream= exchange.getRequestBody().readAllBytes();
        String xmlData = new String(xmlStream);
    }


    @Override
    protected void InitMediaType() {
        super.mediaType= CustomMediaType.APPLICATION_XML;
    }
}
