package org.interview.vehicleregistration.exception.custom;

public class DateParseException extends ApiRequestException {
    public DateParseException(String message) {
        super(message);
    }
}