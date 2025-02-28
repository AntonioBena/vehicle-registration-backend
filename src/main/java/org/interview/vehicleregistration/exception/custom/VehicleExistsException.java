package org.interview.vehicleregistration.exception.custom;

public class VehicleExistsException extends ApiRequestException {
    public VehicleExistsException(String message) {
        super(message);
    }
}