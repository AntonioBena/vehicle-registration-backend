package org.interview.vehicleregistration.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.RegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<?> createAccount(@RequestBody @Valid RegistrationRequest request) throws Exception {
        return authService.registerNewUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<?> createAccount(@RequestBody @Valid AuthenticationRequest request) throws Exception {
        return authService.authenticate(request);
    }
}