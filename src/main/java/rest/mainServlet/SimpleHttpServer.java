package rest.mainServlet;

// Java Program to Set up a Basic HTTP Server
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class SimpleHttpServer
{
    private final static Logger LOGGER= LoggerFactory.getLogger(SimpleHttpServer.class);
    private final int port;
    private final int backlog;

    @Getter
    static HttpHandler rootHandler =  new RootHandler();

    @Getter
    static HttpHandler mimeHandler =  new MimeHandler();

    public static void main(String[] args) throws IOException{
       SimpleHttpServer simpleHttpServer = new SimpleHttpServer(8080, 0);
       simpleHttpServer.start();
    }

    public SimpleHttpServer(int port, int backlog)  {
        this.port = port;
        this.backlog = backlog;
    }



    public void start() throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), backlog);
        server.createContext("/simple", rootHandler);
        server.createContext("/mime",mimeHandler );

        server.setExecutor(null); // Use the default executor
        server.start();
        LOGGER.info("Server is running on port {} ", port);
    }

    static class MimeHandler implements HttpHandler {

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

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            // handle the request
            String response = "Simple response!";
            exchange.sendResponseHeaders(200, response.length());
           try(OutputStream os = exchange.getResponseBody()) {
                 os.write(response.getBytes());
           }
        }
    }
}