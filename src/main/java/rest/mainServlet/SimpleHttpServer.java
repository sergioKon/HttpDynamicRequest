package rest.mainServlet;

// Java Program to Set up a Basic HTTP Server

import com.sun.net.httpserver.HttpServer;
import common.http.request.MimeHttpHandler;
import httpHandlers.HTTPAbstractHandler;
import lombok.Getter;
import lombok.Setter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.base.config.ServiceDispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;


public class SimpleHttpServer
{
    private final static Logger LOGGER=  LogManager.getLogger(SimpleHttpServer.class);
    private final int port;
    private final int backlog;

    @Getter @Setter
    MimeHttpHandler mimeHttpHandler;
    HttpServer server;
    String address;
    public SimpleHttpServer(String address, int port, int backlog)  {
        this.address= address;
        this.port = port;
        this.backlog = backlog;
        init();
    }
    public SimpleHttpServer(int port, int backlog)  {
       this("0.0.0.0", port, backlog);
    }
    public SimpleHttpServer(int port)  {
        this(port,0);
    }

    protected void init(){
        ServiceDispatcher serviceDispatcher = ServiceDispatcher.getInstance();
        Map<CustomMediaType, HTTPAbstractHandler> handlers = serviceDispatcher.getAll();
        handlers.forEach((key, value) -> {
            System.out.println("Media type  = " + key + ", HttpHandler =  " + value.getClass().getName());
        });
        
        mimeHttpHandler =  new MimeHttpHandler();
        mimeHttpHandler.setHttpHandlers(handlers);
    }
    public void start() throws IOException
    {
        server = HttpServer.create(new InetSocketAddress(address,port), backlog);
        
        server.createContext("/mime",mimeHttpHandler );
        server.setExecutor(null); // Use the default executor
        server.start();
        LOGGER.info("Server is running on port {} ", port);

    }


    public void stop() {
       if(server!=null) {
           server.stop(5);
       }
    }
}