package org.interview.vehicleregistration.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private String description;
    private String error;
    private String password;
    private T data;
}