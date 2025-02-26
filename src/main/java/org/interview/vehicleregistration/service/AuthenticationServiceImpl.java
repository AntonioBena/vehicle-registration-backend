package org.interview.vehicleregistration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.repository.UserRepository;
import org.interview.vehicleregistration.configuration.ApplicationProperties;
import org.interview.vehicleregistration.exception.custom.RegisteredUserException;
import org.interview.vehicleregistration.model.dto.UserDto;
import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.RegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static org.interview.vehicleregistration.model.user.UserRole.USER;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties appProperties;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<?> authenticate(AuthenticationRequest request) {
        log.info("Authenticate request: {}", request);
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getAccountId(), request.getPassword()));
        } catch (AuthenticationException exception) {
            log.error("Authentication failed, bad credentials: {}", exception.getMessage());
            throw new BadCredentialsException("Bad credentials", exception);
        }

        log.info("User authenticated successfully! {}", authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var foundUser = userRepository.findByEmail(userDetails.getUsername()).get();

        var authenticatedUserDto = UserDto.builder()
                .id(foundUser.getId().toString())
                .email(foundUser.getEmail())
                .createdAt(foundUser.getCreatedAt())
                .build();
        return ApiResponse
                .builder()
                .success(true)
                .description("User authenticated successfully")
                .data(authenticatedUserDto) // returning user to set wellcome message
                .build();
    }

    @Override
    public ApiResponse<?> registerNewUser(RegistrationRequest request) {
        log.info("Registering new user with request: {}", request);
        if (userRepository.existsByEmail(request.getAccountId())) {
            log.error("User with email {} already exists", request.getAccountId());
            throw new RegisteredUserException("User already registered!");
        }

        var plainPassword = generatePlainPassword();

        var newAccount = UserEntity.builder()
                .email(request.getAccountId())
                .password(
                        passwordEncoder.encode(plainPassword)
                )
                .accountLocked(false)
                .enabled(
                        appProperties.isCreateEnabledUsers()
                )
                .role(USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(newAccount);
        log.info("Account created: {}", newAccount);
        return ApiResponse.builder()
                .success(true)
                .description("Account created!")
                .password(plainPassword)
                .build();
    }

    private String generatePlainPassword() {
        Random random = new Random();

        return random.ints(appProperties.getGeneratedPasswordLeftLimit(),
                        appProperties.getGeneratedPasswordRightLimit() + 1)
                .filter(i ->
                        (i <= 57 || i >= 65) && (i <= 90 || i >= 97)
                )
                .limit(appProperties.getGeneratedPasswordLength())
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();
    }
}

// TODO Parametri su sljedeći:
//- success: true|false
//- description: npr. Proslijeđeni account
//ID već postoji.
//- password: Automatski generisana
//lozinka dužine 8 alfanumeričkih
//znakova
//Primjer:
//{ success: true, description: „Vaš račun
//je uspješno otvoren“, password:
//„A12x4ttr“ }