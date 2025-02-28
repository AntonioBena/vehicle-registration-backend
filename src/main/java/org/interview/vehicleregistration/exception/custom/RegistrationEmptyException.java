package org.interview.vehicleregistration.exception.custom;


public class RegistrationEmptyException extends ApiRequestException {
    public RegistrationEmptyException(String message) {
        super(message);
    }
}
