package org.interview.vehicleregistration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.model.Vehicle;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.model.dto.responses.PageResponse;
import org.interview.vehicleregistration.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatisticsServiceImpl {

    private final VehicleRepository vehicleRepository;

    public ApiResponse<?> getStatisticsByAccountId(String accountId) {
        log.info("Get statistics by account id " + accountId);
        if (vehicleRepository.existsByUser_Email(accountId)) {
            log.error("User with email {} does not exists", accountId); //TODO can be in exception check or something
            throw new IllegalStateException("User does not exist!");
        }
        var vehicleCountByUser = vehicleRepository.countVehicleByUser_Email(accountId);
        return ApiResponse.builder()
                .success(true)
                .data(Map.of(accountId, vehicleCountByUser))
                .build();
    }

    public ApiResponse<?> getStatisticsByAccountId(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.builder()
                .success(true)
                .data(mapVehicleMapPage(pageable))
                .build();
    }

    private PageResponse<?> mapVehicleMapPage(Pageable pageable) {
        Map<String, Long> responseMap = new HashMap<>();
        Page<Vehicle> vehiclesPage = vehicleRepository.findAllBy(pageable);

        vehiclesPage.get().forEach(vehicle -> {
            var userEmail = vehicle.getUser().getEmail();
            responseMap.put(userEmail, vehicleRepository.countVehicleByUser_Email(userEmail));
        });
        log.info("Total number of users and their vehicles {}", responseMap);
        return new PageResponse<>(
                responseMap,
                vehiclesPage.getNumber(),
                vehiclesPage.getSize(),
                vehiclesPage.getTotalElements(),
                vehiclesPage.getTotalPages(),
                vehiclesPage.isLast(),
                vehiclesPage.isFirst()
        );
    }
}

//TODO Server odgovara sa JSON objektom, odnosno
//TODO mapom ključ:vrijednost, gdje je ključ
//TODO accountId, a vrijednost broj automobila koje je
//TODO registrovao taj korisnik.
//TODO Primjer:
//{
//‘testni.test@test.com’: 10, // TODO primjer izgleda kao mapa gdje poyivam sve korisnike i mapiram korisnik : brojRegVozila
//‘testni.test1@test.com’: 3,
//‘testni.test2@test.com’: 42
//}