package org.interview.vehicleregistration.service.vehicle;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.model.Vehicle;
import org.interview.vehicleregistration.model.dto.VehicleDto;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.interview.vehicleregistration.service.DateParser.covertDateToString;
import static org.interview.vehicleregistration.service.DateParser.parseDate;

@RequiredArgsConstructor
@Slf4j
@Service
public class VehicleRegistrationServiceImpl implements VehicleRegistrationService {

    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public ApiResponse<?> registerNewVehicle(VehicleDto request) throws Exception {
        log.info("Registering new vehicle: {}", request);

        if (vehicleRepository.existsByRegistrationId(request.getRegistrationId())) {
            throw new Exception("Vehicle already exists"); //TODO add exception
        }

        var parsedExpirationDate = parseDate(request.getRegistrationExpirationDate());

        var newVehicle = Vehicle.builder()
                .registrationId(request.getRegistrationId())
                .vehicleMake(request.getVehicleMake())
                .vehicleModel(request.getVehicleModel())
                .registrationExpirationDate(parsedExpirationDate)
                .build();
        vehicleRepository.save(newVehicle);

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
                .orElseThrow(() -> new IllegalStateException("Vehicle not found")); //TODO exceptions

        return registrationValidityCheck(vehicle);
    }

    private ApiResponse<?> registrationValidityCheck(Vehicle vehicle) { //TODO object
        return LocalDate.now().isAfter(vehicle.getRegistrationExpirationDate()) ?
                buildValidationResponse("Vehicle registration is not valid",
                        vehicle.getRegistrationExpirationDate()) :
                buildValidationResponse("Vehicle registration is valid",
                        vehicle.getRegistrationExpirationDate());
    }


    private ApiResponse<?> buildValidationResponse(String message, LocalDate validUntil) { //TODO to statics
        return ApiResponse
                .builder()
                .success(true)
                .message(message)
                .validUntil(covertDateToString(validUntil))
                .build();
    }

    //TODO Primjer:
    //{
    //validUntil: “2024-11-18”,
    //message: “Vaša registracija je validna”
    //}
    //Ili
    //{
    //validUntil: “2023-05-01”,
    //message: “Vaša registracija je istekla.”
    //}


    //TODO automobila. Neuspješna registracija
    //se događa samo ako proslijeđene registarske
    //oznake već postoje.
    //Parametri su sljedeći:
    //- success: true|false
    //- description: npr. Proslijeđeni
    //automobil je već registrovan.
    //Primjer:
    //{ success: true, description:
    //„Automobil je uspješno registrovan.“ }
}