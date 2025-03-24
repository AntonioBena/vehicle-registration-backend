package org.interview.vehicleregistration.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

import java.util.List;

import org.springframework.security.config.Customizer;

@Getter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @NotNull
    private final Security security;
    @NotNull
    private final Date date;
    @NotNull
    private final Validation validation;

    public ApplicationProperties(Security security, Date date, Validation validation) {
        this.security = security;
        this.date = date;
        this.validation = validation;
    }

    public record Validation(
            @NotBlank(message = "Email regex is mandatory!")
            String emailRegex,
            @NotBlank(message = "Date regex is mandatory!")
            String dateRegex,
            @NotBlank(message = "Registration number regex is mandatory!")
            String registrationNumberRegex
    ) {
    }

    @Valid
    public record Date(
            @NotBlank
            @Length(min = 10, message = "Date pattern should be at least 9 chars long!")
            String format
    ) {
    }

    @Getter
    @AllArgsConstructor
    public static class Security {

        private final boolean createEnabledUsers;
        private final List<String> allowedHeaders;
        private final List<String> allowedOrigins;
        private final List<String> allowedMethods;
        private final List<String> requestMatchers;
        private final CorsConfiguration corsConfiguration;
        private final PasswordGenerator passwordGenerator;
        private Boolean csrfEnabled;

        public Customizer<CsrfConfigurer<HttpSecurity>> getCsrfConfig() {
            return csrfEnabled
                    ? Customizer.withDefaults()
                    : AbstractHttpConfigurer::disable;
        }

        public record PasswordGenerator(int passwordLength, PasswordLimits passwordLimits) {
            public record PasswordLimits(
                    @Min(48)
                    @Max(55)
                    int left,
                    @Min(100)
                    @Max(122)
                    int right)
            {
            }
        }

        public record CorsConfiguration(
                @NotBlank
                @Length(min = 3, message = "Cors pattern should be at least 3 chars long!")
                String pattern
        ) {}
    }
}