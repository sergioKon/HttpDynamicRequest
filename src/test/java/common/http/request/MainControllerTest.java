package common.http.request;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rest.mainServlet.ServletReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    TestRestTemplate restTemplate;

    @Test  void homeController() {
        ResponseEntity<String> response = restTemplate.getForEntity("/home", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void noContentTest()  {

        ResponseEntity<String> response = restTemplate.getForEntity("/anyTypeClient", String.class);
        assertEquals(response.getBody(),  HttpStatus.NO_CONTENT.name());
    }

    @Test
    public void withContentTest()  {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/anyTypeClient", String.class,params);
        assertEquals(response.getBody(),  HttpStatus.NO_CONTENT.name());
    }
    @Test
    public void octetStreamRequestTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/anyTypeClient")
                       // .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .content("bar".getBytes())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(status().is(HttpStatus.OK.value()));
    }


    @Test
    public void testServletResponse() throws  IOException {
        // Create a new instance of your servlet
        ServletReader servlet = new ServletReader();

        // Mock request and response objects
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent("<1>data</1>".getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setContentType(MediaType.APPLICATION_XML_VALUE);
        servlet.doPost(request, response);

        // Verify the response
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(MediaType.APPLICATION_XML_VALUE);
    }

    @Test
    public void xmlRequestTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/anyTypeClient")
                        .accept(MediaType.APPLICATION_XML)
                        .content("<main>2</main>")
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string( HttpStatus.NO_CONTENT.name()));
    }
}