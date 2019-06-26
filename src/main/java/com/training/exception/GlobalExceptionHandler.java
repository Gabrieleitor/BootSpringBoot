package com.training.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final HttpStatusCodeException ex,
                                                            final WebRequest request ) {
        log.error(ex.getMessage());
        final ErrorResponse errorDetails = ErrorResponse.builder()
            .timestamp(Instant.now())
            .message(ex.getStatusText())
            .path(request.getDescription(false ))
            .status(ex.getStatusCode().value())
            .error(ex.getStatusCode().name())
            .build();
        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

}
