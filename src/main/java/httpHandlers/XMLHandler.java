package httpHandlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;

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
        super.mediaType= MediaType.APPLICATION_XML;
    }
}
