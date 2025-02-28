package org.interview.vehicleregistration.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.service.vehicle.VehicleRegistrationServiceImpl;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping
public class VehicleController {

    private final VehicleRegistrationServiceImpl vehicleRegistrationService;

    @PostMapping(path = "/register")
    public ApiResponse<?> createNewVehicle(@Valid @RequestBody VehicleDto request) throws Exception {
        return vehicleRegistrationService.registerNewVehicle(request);
    }

    @GetMapping(path = "/registration/registrationCode")
    public ApiResponse<?> checkRegistration(@RequestHeader("RegistrationCode") String registrationCode) {
        return vehicleRegistrationService.checkRegistration(registrationCode);
    }
}