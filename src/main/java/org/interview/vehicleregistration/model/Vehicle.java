package org.interview.vehicleregistration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.interview.vehicleregistration.model.user.UserEntity;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
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
    @JoinColumn(name = "user_entity_id", referencedColumnName = "id")
    private UserEntity user;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", registrationExpirationDate=" + registrationExpirationDate +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(vehicleMake, vehicle.vehicleMake) && Objects.equals(vehicleModel, vehicle.vehicleModel) && Objects.equals(registrationId, vehicle.registrationId) && Objects.equals(registrationExpirationDate, vehicle.registrationExpirationDate) && Objects.equals(user, vehicle.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleMake, vehicleModel, registrationId, registrationExpirationDate, user);
    }
}