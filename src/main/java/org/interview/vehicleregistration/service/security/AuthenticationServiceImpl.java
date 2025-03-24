package org.interview.vehicleregistration.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.repository.UserRepository;
import org.interview.vehicleregistration.configuration.ApplicationProperties;
import org.interview.vehicleregistration.exception.custom.RegisteredUserException;
import org.interview.vehicleregistration.model.dto.UserDto;
import org.interview.vehicleregistration.model.dto.requests.AuthenticationRequest;
import org.interview.vehicleregistration.model.dto.requests.UserRegistrationRequest;
import org.interview.vehicleregistration.model.dto.responses.ApiResponse;
import org.interview.vehicleregistration.model.user.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        var authenticatedUserDto = revealAuthenticatedUserAndMapToDto(authentication);

        return ApiResponse
                .builder()
                .success(true)
                .description("User authenticated successfully")
                .data(authenticatedUserDto)
                .build();
    }

    private UserDto revealAuthenticatedUserAndMapToDto(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var foundUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Can not find logged in user: " + userDetails.getUsername()));

        return UserDto.builder()
                .id(foundUser.getId().toString())
                .email(foundUser.getEmail())
                .firstName(foundUser.getFirstName())
                .lastName(foundUser.getLastName())
                .createdAt(foundUser.getCreatedAt())
                .build();
    }

    @Override
    public ApiResponse<?> registerNewUser(UserRegistrationRequest request) {
        log.info("Registering new user with request: {}", request);
        if (userRepository.existsByEmail(request.getAccountId())) {
            log.error("User with email {} already exists", request.getAccountId());
            throw new RegisteredUserException("User already registered!");
        }

        var plainPassword = generatePlainTextPassword();

        var newAccount = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getAccountId())
                .password(
                        passwordEncoder.encode(plainPassword)
                )
                .accountLocked(false)
                .enabled(
                        appProperties
                                .getSecurity()
                                .isCreateEnabledUsers()
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

    private String generatePlainTextPassword() {
        Random random = new Random();

        return random.ints(
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLimits()
                                .left(),
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLimits()
                                .right() + 1)
                .filter(i ->
                        (i <= 57 || i >= 65) && (i <= 90 || i >= 97)
                )
                .limit(
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLength())
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();
    }
}