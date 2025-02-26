package org.interview.vehicleregistration.exception;

import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.exception.custom.RegisteredUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.interview.vehicleregistration.exception.ErrorCodes.ACCOUNT_IS_REGISTERED;
import static org.interview.vehicleregistration.exception.ErrorCodes.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse
                                .builder()
                                .success(false)
                                .description("Internal error, please contact admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(RegisteredUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegisteredUserException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(ACCOUNT_IS_REGISTERED.getCode())
                                .description(ACCOUNT_IS_REGISTERED.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(BAD_CREDENTIALS.getCode())
                                .description(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

    //TODO UsernameNotFoundException
}