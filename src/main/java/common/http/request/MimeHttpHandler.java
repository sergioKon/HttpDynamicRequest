package common.http.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpHandlers.HTTPAbstractHandler;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@Setter
@Getter
public class MimeHttpHandler implements HttpHandler {
    private final static Logger LOGGER=  LogManager.getLogger(MimeHttpHandler.class);
    private Map<CustomMediaType, HTTPAbstractHandler> httpHandlers;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "get":
                doGet();
                break;
            case "post":
                doPost();
                break;
        }

        Headers headers = exchange.getRequestHeaders();
        String contentType = headers.getFirst("Content-Type");
        if (contentType == null) {
            contentType = "";
        }
        HTTPAbstractHandler httpHandler = httpHandlers.get(CustomMediaType.valueOf(contentType));
        String responseMessage;
        int responseCode;
        if (httpHandler == null) {
            responseMessage= "There is no handler for this type ";
            responseCode=400;

        }
        else {
            httpHandler.proceed(exchange);
            responseCode = 200;
            responseMessage= "Success";
            LOGGER.debug(" httpHandler{}  content  type = {}", httpHandler.getClass().getName(), contentType);
        }
        exchange.sendResponseHeaders(responseCode, responseMessage.length());

        try( OutputStream os = exchange.getResponseBody()){
            os.write(responseMessage.getBytes());
        }
    }

    private void doPost() {
    }

    private void doGet() {
    }

}