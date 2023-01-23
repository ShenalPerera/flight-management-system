package com.fms.flights.exceptions;

public class SameArrivalDepartureException extends RuntimeException{
    public SameArrivalDepartureException(String errorMessage){
        super(errorMessage);
    }
}
