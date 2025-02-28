package org.interview.vehicleregistration.model.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;


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
    private String validUntil;
    private T data;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", error='" + error + '\'' +
                ", password='" + password + '\'' +
                ", validUntil='" + validUntil + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return success == that.success && Objects.equals(message, that.message) && Objects.equals(description, that.description) && Objects.equals(error, that.error) && Objects.equals(password, that.password) && Objects.equals(validUntil, that.validUntil) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, description, error, password, validUntil, data);
    }
}