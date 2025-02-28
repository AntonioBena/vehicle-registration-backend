package org.interview.vehicleregistration.exception.custom;

public class VehicleNotFoundException extends ApiRequestException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}