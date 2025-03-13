package org.interview.vehicleregistration.service.vehicle;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.exception.custom.RegistrationDateIsNotPresentException;
import org.interview.vehicleregistration.exception.custom.RegistrationEmptyException;
import org.interview.vehicleregistration.exception.custom.VehicleExistsException;
import org.interview.vehicleregistration.exception.custom.VehicleNotFoundException;
import org.interview.vehicleregistration.model.Vehicle;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.interview.vehicleregistration.repository.UserRepository;
import org.interview.vehicleregistration.repository.VehicleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.interview.vehicleregistration.service.DateParser.parseDate;

@RequiredArgsConstructor
@Slf4j
@Service
public class VehicleRegistrationServiceImpl implements VehicleRegistrationService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ApiResponse<?> registerNewVehicle(VehicleDto request) {
        log.info("Registering new vehicle: {}", request);

        if (vehicleRepository.existsByRegistrationId(request.getRegistrationId())) {
            throw new VehicleExistsException("Vehicle already exists");
        } else if (request.getRegistrationId() == null || request.getRegistrationId().isEmpty()) {
            throw new RegistrationEmptyException("Registration id is empty");
        } else if (request.getRegistrationExpirationDate() == null ||
                request.getRegistrationExpirationDate().isEmpty()) {
            throw new RegistrationDateIsNotPresentException("Registration expiration Date is empty");
        }

        var parsedExpirationDate = parseDate(request.getRegistrationExpirationDate());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var foundUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        attachVehicleToUserAndSave(foundUser, request, parsedExpirationDate);

        return ApiResponse
                .builder()
                .success(true)
                .description("Vehicle registered successfully")
                .build();
    }

    @Override
    public ApiResponse<?> checkRegistration(String registrationCode) {
        log.info("Checking registration is valid for: {}", registrationCode);

        var vehicle = vehicleRepository.findVehicleByRegistrationId(registrationCode)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

        return registrationValidityCheck(vehicle);
    }

    private void attachVehicleToUserAndSave(UserEntity foundUser, VehicleDto request,
                                            LocalDate registrationExpirationDate) {
        var newVehicle = Vehicle.builder()
                .user(foundUser)
                .registrationId(request.getRegistrationId())
                .vehicleMake(request.getVehicleMake())
                .vehicleModel(request.getVehicleModel())
                .registrationExpirationDate(registrationExpirationDate)
                .build();
        var savedVehicle = vehicleRepository.save(newVehicle);

        var vehicles = foundUser.getVehicles();
        vehicles.add(savedVehicle);
        foundUser.setVehicles(vehicles);
        userRepository.save(foundUser);
    }

    private ApiResponse<?> registrationValidityCheck(Vehicle vehicle) {
        boolean isValid = !LocalDate.now().isAfter(vehicle.getRegistrationExpirationDate());
        String message = isValid ? "Vehicle registration is valid" : "Vehicle registration is not valid";

        return
                buildValidationResponse(message, vehicle.getRegistrationExpirationDate().toString());
    }


    private ApiResponse<?> buildValidationResponse(String message, String validUntil) {
        return ApiResponse
                .builder()
                .success(true)
                .message(message)
                .validUntil(validUntil)
                .build();
    }
}