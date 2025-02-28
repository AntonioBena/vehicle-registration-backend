package org.interview.vehicleregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.vehicleregistration.BaseTest;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest extends BaseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testShouldNotGetNonExistingStatisticsByAccountId() throws Exception {
        mockMvc.perform(get("/statistics/{accountId}", "test@id55")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@id55", getTestPassword())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldGetStatisticsByAccountId() throws Exception {
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

        mockMvc.perform(get("/statistics/{accountId}", "test@id55")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@id55", getTestPassword())))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getAllStatistics() throws Exception {
        mockMvc.perform(get("/statistics/all-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@id55", getTestPassword())))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
    }
}