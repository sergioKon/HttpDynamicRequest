package rest.mainServlet;

// Java Program to Set up a Basic HTTP Server

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;


public class SimpleHttpServer
{
    private final static Logger LOGGER= LoggerFactory.getLogger(SimpleHttpServer.class);
    private final int port;
    private final int backlog;

    @Getter @Setter
    HttpHandler mimeHandler;

    public SimpleHttpServer(int port, int backlog)  {
        this.port = port;
        this.backlog = backlog;
    }
    public SimpleHttpServer(int port)  {
        this(port,0);
    }

    public void start() throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), backlog);
        server.createContext("/mime",mimeHandler );
        server.setExecutor(null); // Use the default executor
        server.start();
        LOGGER.info("Server is running on port {} ", port);
    }

}