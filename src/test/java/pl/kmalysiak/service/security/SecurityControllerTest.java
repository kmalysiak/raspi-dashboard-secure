package pl.kmalysiak.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityControllerTest {

    private MockMvc mockMvc;

    private String currentToken;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        currentToken = registerUser();
    }

    String registerUser() throws Exception {

        String response = mockMvc
                .perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{ \"username\": \"admin\", \"password\": \"admin\" }")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        HashMap<String, String> result = new ObjectMapper().readValue(response, HashMap.class);

        return result.get("token");
    }

    @Test
    public void getAuthenticationToken() throws Exception {

        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\": \"admin\" }").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        // TODO: check the generation of token and assure it is correct
        /*
         * .andExpect( jsonPath("$.token") .value(
         * "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU2MzE0NjYwOCwiaWF0IjoxNTYzMTI4NjA4fQ.dk6jz8Oh9-680mh5_qNvbWqcJ1-WebpXFOO4KjnUZfQt97juQ28jX81MAcjBlIGPyWh8ySNNMJRKO_hHzjD93A"
         * ));
         */
    }

    @Test
    public void getErrorWhenIncorrectUser() throws Exception {

        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"adminn\", \"password\": \"admin\" }").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void getErrorWhenIncorrectPassword() throws Exception {

        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\": \"adminn\" }").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getErrorWhenEmptyPassword() throws Exception {

        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\": \"\" }").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getErrorWhenNoPassword() throws Exception {

        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\" }").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void getCorrectResponseAfterTokenAuthorization() throws Exception {

        mockMvc.perform(get("/resource").header("Authorization", "Bearer " + currentToken)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getErrorWhenNoToken() throws Exception {

        ResultActions aa = mockMvc
                .perform(get("/resource").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        // .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

  /*  @Test
    public void getErrorWhenIncorrectToken() throws Exception {

        mockMvc.perform(get("/resource").header("Authorization", currentToken + "_")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }*/

}
