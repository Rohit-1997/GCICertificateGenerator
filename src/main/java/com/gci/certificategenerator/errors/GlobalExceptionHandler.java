package com.gci.certificategenerator.errors;

import com.gci.certificategenerator.errors.custom.ApplicationException;
import com.gci.certificategenerator.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = Loggers.APPLICATION_LOG;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(Throwable ex) {
        log.error("global application exception:: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAnyException(Throwable ex) {
        log.error("generic exception:: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
