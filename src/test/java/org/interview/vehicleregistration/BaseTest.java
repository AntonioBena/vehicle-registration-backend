package org.interview.vehicleregistration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.interview.vehicleregistration.TestUtils.generateUserRegistrationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private String testPassword = "";

    public String getTestPassword() {
        return testPassword;
    }

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        var request = generateUserRegistrationRequest("test@id55", "name", "lastName");
        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ApiResponse.class);
        testPassword = resultToResp.getPassword();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}
