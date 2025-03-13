package common.http.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpHandlers.HTTPAbstractHandler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


public class MimeHandler implements HttpHandler {
    private final static Logger LOGGER= LoggerFactory.getLogger(MimeHandler.class);

    @Setter @Getter
    private Map<CustomMediaType, HTTPAbstractHandler> httpHandlers;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers headers=  exchange.getRequestHeaders();
        String contentType =  headers.getFirst("Content-Type");
        if(contentType==null) {
            contentType="";
        }
        LOGGER.debug(" content  type = {}", contentType);
        exchange.sendResponseHeaders(200, contentType.length());
        try( OutputStream os = exchange.getResponseBody()){
            os.write(contentType.getBytes());
        }
    }

}