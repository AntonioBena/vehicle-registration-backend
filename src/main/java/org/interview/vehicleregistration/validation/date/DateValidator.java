package org.interview.vehicleregistration.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.interview.vehicleregistration.validation.email.EmailValidatorImpl;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValidator {
    String message() default "Date Malformed!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}