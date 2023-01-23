package com.fms.flights.exceptions;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String errorMessage){
        super(errorMessage);
    }
}
