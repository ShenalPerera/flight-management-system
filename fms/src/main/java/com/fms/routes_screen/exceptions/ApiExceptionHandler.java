package com.fms.routes_screen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {DuplicateRouteException.class})
    public ResponseEntity<Object> handleDuplicateRouteException(DuplicateRouteException e) {
        ApiException apiException =
                new ApiException(e.getMessage(), HttpStatus.CONFLICT, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {InvalidFormException.class})
    public ResponseEntity<Object> handleInvalidFormException(InvalidFormException e) {
        ApiException apiException =
                new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingFieldsException.class})
    public ResponseEntity<Object> handleMissingFieldsException(MissingFieldsException e) {
        ApiException apiException =
                new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IdNotFoundException.class})
    public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException e) {
        ApiException apiException =
                new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

}
