package common.http.request;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rest.mainServlet.SimpleHttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class SimpleHttpServerTest {
    private static HttpServer server;
    private static final int PORT = 8080;
    private static HttpClient client;
    @BeforeAll
    static void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/simple", SimpleHttpServer.getRootHandler());
        server.createContext("/mime", SimpleHttpServer.getMimeHandler());
        server.setExecutor(null);
        server.start();
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    static void tearDown() {
        server.stop(0);
    }

    @Test
    void testXMLType() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/mime"))
                .GET()
                .header("Content-Type", "application/xml") // Setting Content-Type
                .POST(HttpRequest.BodyPublishers.ofString("<data>data</data>")) // JSON body
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        System.out.println(" response body = "+ response.body());
      //  assertEquals("Simple response!", response.body());
        server.stop(0);
    }
    @Test
    void testHelloEndpoint() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/simple"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Simple response!", response.body());
        server.stop(0);
    }
}
