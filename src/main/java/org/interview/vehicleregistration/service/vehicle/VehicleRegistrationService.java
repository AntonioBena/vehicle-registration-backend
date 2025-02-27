package org.interview.vehicleregistration.service.vehicle;

import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;

public interface VehicleRegistrationService {
    ApiResponse<?> registerNewVehicle(VehicleDto request) throws Exception;
    ApiResponse<?> checkRegistration(String registrationCode);
}