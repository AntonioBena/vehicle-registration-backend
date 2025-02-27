package org.interview.vehicleregistration.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private VehicleDto vehicle;

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(email, userDto.email) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(createdAt, userDto.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, createdAt);
    }
}