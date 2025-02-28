package org.interview.vehicleregistration.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
public enum ErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect password"),
    PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "Account disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Email and / or password is incorrect"),
    ACCOUNT_IS_REGISTERED(305, FORBIDDEN, "Account already created"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Not found"),
    ALREADY_EXISTS(409, HttpStatus.CONFLICT, "Entity lready exists"),
    REGISTRATION_IS_NOT_PRESENT(400, BAD_REQUEST, "Registration is not present"),
    DATE_FORMAT_INVALID(400, BAD_REQUEST, "Date format is invalid"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}