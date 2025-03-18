package httpHandlers;


import com.sun.net.httpserver.HttpExchange;
import converters.DataParser;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.mainServlet.CustomMediaType;

import java.io.IOException;


public abstract class HTTPAbstractHandler {


    @Getter
    protected CustomMediaType mediaType;
    protected  DataParser dataParser = null;
    protected final Logger logger = LogManager.getLogger(getClass());
    public HTTPAbstractHandler() {
        logger.debug(" you are handling {} client mime type ",getClass().getName());
        InitMediaType();
    }
    protected abstract void InitMediaType();
    abstract public  void proceed(HttpExchange exchange) throws IOException;
}
