package org.interview.vehicleregistration.exception;

import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.exception.custom.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.interview.vehicleregistration.exception.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(ErrorCodes.NOT_FOUND.getCode())
                                .description(ErrorCodes.NOT_FOUND.getDescription())
                                .error(ErrorCodes.NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(VehicleExistsException.class)
    public ResponseEntity<ExceptionResponse> handleException(VehicleExistsException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(ALREADY_EXISTS.getCode())
                                .description(ALREADY_EXISTS.getDescription())
                                .error(ALREADY_EXISTS.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(RegistrationEmptyException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationEmptyException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(REGISTRATION_IS_NOT_PRESENT.getCode())
                                .description(REGISTRATION_IS_NOT_PRESENT.getDescription())
                                .error(REGISTRATION_IS_NOT_PRESENT.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(RegistrationDateIsNotPresentException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegistrationDateIsNotPresentException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(REGISTRATION_IS_NOT_PRESENT.getCode())
                                .description(REGISTRATION_IS_NOT_PRESENT.getDescription())
                                .error(REGISTRATION_IS_NOT_PRESENT.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(VehicleNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(ErrorCodes.NOT_FOUND.getCode())
                                .description(ErrorCodes.NOT_FOUND.getDescription())
                                .error(ErrorCodes.NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(DateParseException.class)
    public ResponseEntity<ExceptionResponse> handleException(DateParseException exp) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(
                        ExceptionResponse.builder()
                                .success(false)
                                .code(DATE_FORMAT_INVALID.getCode())
                                .description(DATE_FORMAT_INVALID.getDescription())
                                .error(DATE_FORMAT_INVALID.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}