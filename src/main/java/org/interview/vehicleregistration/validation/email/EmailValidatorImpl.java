package org.interview.vehicleregistration.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.configuration.ApplicationProperties;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {
    private final ApplicationProperties appProperties;

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
        Pattern pattern = Pattern.compile(
                appProperties
                        .getValidation()
                        .emailRegex()
        );
        return emailField != null && pattern.matcher(emailField).matches();
    }
}