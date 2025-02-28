package org.interview.vehicleregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.vehicleregistration.BaseTest;
import org.interview.vehicleregistration.exception.ExceptionResponse;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.interview.vehicleregistration.TestUtils.generateAuthRequest;
import static org.interview.vehicleregistration.TestUtils.generateUserRegistrationRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testShouldCreateNewUserAndReturnPassword() throws Exception {
        var request = generateUserRegistrationRequest("test@id", "name", "lastName");

        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ApiResponse.class);

        assertTrue(resultToResp.isSuccess());
        assertEquals(8, resultToResp.getPassword().length());
    }

    @Test
    void testShouldAuthenticateUserWithAccount() throws Exception {
        var request = generateAuthRequest("test@id55", getTestPassword());

        var result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ApiResponse.class);
        assertEquals("User authenticated successfully", resultToResp.getDescription());
    }

    @Test
    void testShouldNotAuthenticateUserWithoutAccount() throws Exception {
        var request = generateAuthRequest("test@id", "pass1234569");

        var result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ExceptionResponse.class);

        assertFalse(resultToResp.isSuccess());
        assertEquals("Email and / or password is incorrect", resultToResp.getDescription());
    }

    @Test
    void testShouldNotCreateExistingUser() throws Exception {
        var request = generateUserRegistrationRequest("test@id55", "name", "lastName");

        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ExceptionResponse.class);

        assertFalse(resultToResp.isSuccess());
        assertEquals("Account already created", resultToResp.getDescription());
    }
}