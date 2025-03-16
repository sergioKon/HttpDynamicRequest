package httpHandlers;


import com.sun.net.httpserver.HttpExchange;
import converters.DataParser;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;


public abstract class HTTPAbstractHandler {


    @Getter
    protected CustomMediaType mediaType;
    protected  DataParser dataParser = null;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    public HTTPAbstractHandler() {
     //   log.debug(" you are handling {} client mime type ",this.getClass().getSimpleName());
        InitMediaType();
    }

    protected abstract void InitMediaType();


    abstract public  void proceed(HttpExchange exchange) throws IOException;

    private  HttpRequest.BodyPublisher getRequestBody(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod()) || "PUT".equalsIgnoreCase(exchange.getRequestMethod())) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            return HttpRequest.BodyPublishers.ofString(body);
        }
        return HttpRequest.BodyPublishers.noBody();
    }


}
