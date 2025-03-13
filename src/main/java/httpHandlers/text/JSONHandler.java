package httpHandlers.text;

import httpHandlers.HTTPAbstractHandler;
import jakarta.servlet.http.HttpServletRequest;
import rest.mainServlet.CustomMediaType;


import java.io.IOException;


public class JSONHandler  extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType= CustomMediaType.APPLICATION_JSON;
    }

    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        byte[] jSonStream= request.getInputStream().readAllBytes();
        String jsonData = new String(jSonStream);
    }
}
