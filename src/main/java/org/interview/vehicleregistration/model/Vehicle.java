package org.interview.vehicleregistration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.interview.vehicleregistration.model.user.UserEntity;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle") //TODO equals and to striong
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vehicleMake;
    private String vehicleModel;
    @NotEmpty(message = "Plate number is mandatory")
    @NotBlank(message = "Plate number is mandatory")
    private String registrationId;
    @NotNull(message = "Plate number is mandatory")
    private LocalDate registrationExpirationDate;

    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity user;
}