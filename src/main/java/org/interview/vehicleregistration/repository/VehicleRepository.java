package org.interview.vehicleregistration.repository;

import org.interview.vehicleregistration.model.Vehicle;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    boolean existsByRegistrationId(String registrationNumber);
    //Page<Vehicle> findAllByPage(Pageable pageable);
    Optional<Vehicle> findVehicleByRegistrationId(String registrationNumber);
    Page<Vehicle> findAllBy(Pageable pageable);
    Long countVehicleByUser_Email(String email);
    String user(UserEntity user);
    boolean existsByUser_Email(String email);
}