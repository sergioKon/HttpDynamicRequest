package common.http.request;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rest.mainServlet.CustomMediaType;
import rest.mainServlet.SimpleHttpServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SimpleHttpServerTest {
    private static SimpleHttpServer simpleHttpServer;
    private static final int PORT = 8080;
    private static HttpClient client;
    @BeforeAll
    static void setUp() throws IOException {
        simpleHttpServer = new SimpleHttpServer(PORT);
        Application application= new Application();
        application.setHandlers();
        simpleHttpServer.start(application.getMimeHttpHandler());
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    static void tearDown() {
        if(simpleHttpServer!=null) {
            simpleHttpServer.stop();
        }
    }

    @Test
    void testXMLType() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/mime"))
                .GET()
                .header("Content-Type", CustomMediaType.APPLICATION_XML.name()) // Setting Content-Type
                .POST(HttpRequest.BodyPublishers.ofString("<data>data</data>")) // JSON body
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        System.out.println(" response body = "+ response.body());

    }
    @Test
    void testHelloEndpoint() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/mime"))
                .GET()
                .header("Content-Type", CustomMediaType.TEXT_PLAIN.name())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(400, response.statusCode());
        assertEquals("There is no handler for this type".trim(), response.body().trim());

    }
}
