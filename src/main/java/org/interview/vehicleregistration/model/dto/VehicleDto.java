package org.interview.vehicleregistration.model.dto;

import lombok.*;
import org.interview.vehicleregistration.validation.date.DateValidator;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDto {
    private String vehicleId;
    private String vehicleMake;
    private String vehicleModel;
    private String registrationId;
    @DateValidator(message = "Date pattern is not matched")
    private String registrationExpirationDate;
    private UserDto user;

    @Override
    public String toString() {
        return "VehicleDto{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", registrationExpirationDate='" + registrationExpirationDate + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDto that = (VehicleDto) o;
        return Objects.equals(vehicleId, that.vehicleId) && Objects.equals(vehicleMake, that.vehicleMake)
                && Objects.equals(vehicleModel, that.vehicleModel)
                && Objects.equals(registrationId, that.registrationId)
                && Objects.equals(registrationExpirationDate, that.registrationExpirationDate) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, vehicleMake, vehicleModel, registrationId, registrationExpirationDate, user);
    }
}