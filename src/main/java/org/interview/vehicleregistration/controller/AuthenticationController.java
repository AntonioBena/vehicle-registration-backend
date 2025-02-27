package org.interview.vehicleregistration.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.UserRegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<?> createAccount(@RequestBody @Valid UserRegistrationRequest request) throws Exception {
        return authService.registerNewUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<?> authenticate(@RequestBody @Valid AuthenticationRequest request) throws Exception {
        return authService.authenticate(request);
    }
}