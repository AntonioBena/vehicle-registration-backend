package org.interview.vehicleregistration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.model.dto.responses.PageResponse;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.interview.vehicleregistration.repository.UserRepository;
import org.interview.vehicleregistration.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public ApiResponse<?> getStatisticsByAccountId(String accountId) {
        log.info("Get statistics by account id {}", accountId);
        if (!vehicleRepository.existsByUser_Email(accountId)) {
            log.error("User with email {} does not exists", accountId);
            throw new UsernameNotFoundException("User does not exist!");
        }
        var vehicleCountByUser = vehicleRepository.countVehicleByUser_Email(accountId);
        return ApiResponse.builder()
                .success(true)
                .data(Map.of(accountId, vehicleCountByUser))
                .build();
    }

    public ApiResponse<?> getAllAccountIdStatistics(int page, int size) {
        log.info("Get statistics by account id with page " + page + " and size " + size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.builder()
                .success(true)
                .data(mapVehicleMapPage(pageable))
                .build();
    }

    private PageResponse<?> mapVehicleMapPage(Pageable pageable) {
        Map<String, Integer> responseMap = new HashMap<>();
        Page<UserEntity> usersPage = userRepository.findAllBy(pageable);
        usersPage.get().forEach(u -> {
            var numOfRegistered = u.getVehicles().size();
            responseMap.put(u.getEmail(), numOfRegistered);
        });
        log.info("Total number of users and their vehicles {}", responseMap);
        return new PageResponse<>(
                responseMap,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast(),
                usersPage.isFirst()
        );
    }
}