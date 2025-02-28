package org.interview.vehicleregistration.repository;

import org.interview.vehicleregistration.model.Vehicle;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("""
            SELECT COUNT(v) > 0 FROM Vehicle v WHERE LOWER(v.registrationId) = LOWER(:registrationNumber)
            """)
    boolean existsByRegistrationId(String registrationNumber);

    @Query("""
            SELECT v from Vehicle v WHERE LOWER(v.registrationId) = LOWER(:registrationNumber)
            """)
    Optional<Vehicle> findVehicleByRegistrationId(String registrationNumber); //ignore casse

    Page<Vehicle> findAllBy(Pageable pageable);

    Long countVehicleByUser_Email(String email);

    String user(UserEntity user);

    boolean existsByUser_Email(String email);
}