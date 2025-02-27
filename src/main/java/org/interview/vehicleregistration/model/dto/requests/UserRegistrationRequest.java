package org.interview.vehicleregistration.model.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //TODO optimize
@EqualsAndHashCode
@ToString
public class UserRegistrationRequest {
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String accountId;
    private String firstName;
    private String lastName;
}