package org.interview.vehicleregistration.service;

import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.RegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;

public interface AuthenticationService {
    ApiResponse<?> registerNewUser(RegistrationRequest request) throws Exception;
    ApiResponse<?> authenticate(AuthenticationRequest request) throws Exception;
}