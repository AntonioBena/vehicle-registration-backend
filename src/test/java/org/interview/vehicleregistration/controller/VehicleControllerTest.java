package org.interview.vehicleregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.vehicleregistration.BaseTest;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testCanNotCreateNewVehicleRegistrationWithoutUserAccountId() throws Exception {
        var request = new VehicleDto();
        request.setRegistrationId("registrationId");
        request.setVehicleModel("model");
        request.setVehicleMake("make");
        request.setRegistrationExpirationDate("2025-08-25");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("username", "password"))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testShouldCreateNewVehicleRegistrationWithUserAccount() throws Exception {
        var request = new VehicleDto();
        request.setRegistrationId("registrationId");
        request.setVehicleModel("model");
        request.setVehicleMake("make");
        request.setRegistrationExpirationDate("2025-08-25");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@id55", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testCanCheckVehicleExistsByRegistration() throws Exception {
        var request = new VehicleDto();
        request.setRegistrationId("registrationId");
        request.setVehicleModel("model");
        request.setVehicleMake("make");
        request.setRegistrationExpirationDate("2025-08-25");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@id55", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/registration/registrationCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("RegistrationCode", request.getRegistrationId())
                        .with(httpBasic("test@id55", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }
}