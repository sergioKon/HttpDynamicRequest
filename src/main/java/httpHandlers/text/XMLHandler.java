package httpHandlers.text;

import httpHandlers.HTTPAbstractHandler;
import jakarta.servlet.http.HttpServletRequest;
import rest.mainServlet.CustomMediaType;


import java.io.IOException;


/**
 *
 */
public class XMLHandler extends HTTPAbstractHandler {

    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        byte[] xmlStream= request.getInputStream().readAllBytes();
        String xmlData = new String(xmlStream);
    }


    @Override
    protected void InitMediaType() {
        super.mediaType= CustomMediaType.APPLICATION_XML;
    }
}
