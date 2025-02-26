package org.interview.vehicleregistration.exception.custom;

public class RegisteredUserException extends ApiRequestException {
    public RegisteredUserException(String message) {
        super(message);
    }

    public RegisteredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}