package org.interview.vehicleregistration.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Configuration
public class ApplicationProperties {
    @Value("${application.security.create-enabled-users}")
    private boolean createEnabledUsers;

    @Value("${application.security.generated.password.length}")
    private int generatedPasswordLength;

    @Value("${application.security.generated.password.left.limit}")
    private int generatedPasswordLeftLimit;

    @Value("${application.security.generated.password.right.limit}")
    private int generatedPasswordRightLimit;

    @Value("${application.security.header.registration-code}")
    private String registrationCodeCustomHeader;

    @Value("${application.security.allowed.origin}")
    private String allowedOrigins;

    @Value("${local.date.pattern}")
    private String datePattern;
}