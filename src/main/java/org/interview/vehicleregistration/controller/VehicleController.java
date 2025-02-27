package org.interview.vehicleregistration.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.service.vehicle.VehicleRegistrationServiceImpl;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/register")
public class VehicleController {

    private final VehicleRegistrationServiceImpl vehicleRegistrationService;

    @PostMapping
    public ApiResponse<?> createNewVehicle(@Valid @RequestBody VehicleDto request) throws Exception {
        return vehicleRegistrationService.registerNewVehicle(request);
    }

    @GetMapping(path = "/registration/{registrationCode}")
    public ApiResponse<?> checkRegistration(@PathVariable String registrationCode) throws Exception {
        return vehicleRegistrationService.checkRegistration(registrationCode);
    }

}