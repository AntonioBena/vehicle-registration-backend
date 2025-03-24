package org.interview.vehicleregistration.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.interview.vehicleregistration.configuration.ApplicationProperties;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DateValidatorImpl implements ConstraintValidator<DateValidator, String> {
    private final ApplicationProperties appProperties;

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(
                appProperties
                        .getValidation()
                        .dateRegex()
        );
        return date != null && pattern.matcher(date).matches();
    }
}