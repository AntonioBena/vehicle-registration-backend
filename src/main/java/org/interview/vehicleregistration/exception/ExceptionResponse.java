package org.interview.vehicleregistration.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private boolean success;
    private Integer code;
    private String description;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "success=" + success +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", error='" + error + '\'' +
                ", validationErrors=" + validationErrors +
                ", errors=" + errors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionResponse that = (ExceptionResponse) o;
        return success == that.success && Objects.equals(code, that.code) && Objects.equals(description, that.description) && Objects.equals(error, that.error) && Objects.equals(validationErrors, that.validationErrors) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, code, description, error, validationErrors, errors);
    }
}