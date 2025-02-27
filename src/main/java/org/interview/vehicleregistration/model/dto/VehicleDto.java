package org.interview.vehicleregistration.model.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private String registrationExpirationDate;

//    Registarske oznake (String, obavezan)
//- Datum isteka registracije (Date,
//                             obavezan))
}
