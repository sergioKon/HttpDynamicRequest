package httpHandlers.text;

import com.sun.net.httpserver.HttpExchange;
import httpHandlers.HTTPAbstractHandler;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;


public class JSONHandler  extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType= CustomMediaType.APPLICATION_JSON;
    }

    @Override
    public void proceed(HttpExchange exchange) throws IOException {
        byte[] jSonStream= exchange.getRequestBody().readAllBytes();
        String jsonData = new String(jSonStream);
    }

}
