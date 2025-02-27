package org.interview.vehicleregistration.service.security;

import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.UserRegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;

public interface AuthenticationService {
    ApiResponse<?> registerNewUser(UserRegistrationRequest request) throws Exception;
    ApiResponse<?> authenticate(AuthenticationRequest request) throws Exception;
}