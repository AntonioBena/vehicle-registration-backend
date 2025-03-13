package org.interview.vehicleregistration.model.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.interview.vehicleregistration.validation.EmailValidator;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    @EmailValidator(message = "Email is not formatted correctly!")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String accountId;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "accountId='" + accountId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationRequest that = (UserRegistrationRequest) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, firstName, lastName);
    }
}