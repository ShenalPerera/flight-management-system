package com.fms.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ExceptionHandlerFMS {
    @ExceptionHandler(FMSException.class)
    public ResponseEntity<?> handleException(FMSException exception){
        return  ResponseEntity.status(exception.getStatusCodeValue()).body(null);
    }
}
