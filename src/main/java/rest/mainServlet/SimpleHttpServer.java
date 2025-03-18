package rest.mainServlet;

// Java Program to Set up a Basic HTTP Server

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

@Getter @Setter
public class SimpleHttpServer
{
    private final static Logger LOGGER=  LogManager.getLogger(SimpleHttpServer.class);
    private  int port;
    private  int backlog;

    private String address;
    private HttpServer server;
    public SimpleHttpServer(String address, int port)  {
        this.address= address;
        this.port = port;

    }
    public SimpleHttpServer(int port)  {
       this("127.0.0.1", port);
    }

    public void start(HttpHandler httpHandler) throws IOException {
            server = HttpServer.create(new InetSocketAddress(address,port), backlog);
            server.createContext("/mime",httpHandler );
            server.setExecutor(null); // Use the default executor
            server.start();
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    LOGGER.info("server: {} port{}", address, port );
                }
            }, 0, 10* 1000); // Initial delay: 0 ms, Interval: 10,000 ms (10 seconds)
        }

    public void stop() {
       if(server!=null) {
           server.stop(5);
       }
    }
}