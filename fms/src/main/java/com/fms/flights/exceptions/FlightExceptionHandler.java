package com.fms.flights.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.http.HttpClient;

@RestControllerAdvice
public class FlightExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleException(RuntimeException e){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
    }
}
