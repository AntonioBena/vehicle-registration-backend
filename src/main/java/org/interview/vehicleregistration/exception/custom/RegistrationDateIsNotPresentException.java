package org.interview.vehicleregistration.exception.custom;

public class RegistrationDateIsNotPresentException extends ApiRequestException{
    public RegistrationDateIsNotPresentException(String message) {
        super(message);
    }
}
