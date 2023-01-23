package com.fms.flights.exceptions;

public class InvalidDatesException extends RuntimeException{
    public InvalidDatesException(String errorMessage){
        super(errorMessage);
    }
}
