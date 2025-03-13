package common.http.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

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
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(),  "The content can't be empty");
    }

    @Test
    public void withContentTest()  {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/anyTypeClient", String.class,params);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
        assertEquals(response.getBody(), "The content can't be empty");
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
    public void xmlRequestTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/anyTypeClient")
                        .accept(MediaType.APPLICATION_XML)
                        .content("<main>2</main>")
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }
}