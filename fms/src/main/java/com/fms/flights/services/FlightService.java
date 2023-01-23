package com.fms.flights.services;

import com.fms.fares.exceptions.DuplicateEntryException;
import com.fms.fares.exceptions.SameLocationException;
import com.fms.flights.DTOs.Flight;
import com.fms.flights.FlightRepositoryJSON;
import com.fms.flights.exceptions.EmptyFieldException;
import com.fms.flights.exceptions.InvalidDatesException;
import com.fms.flights.exceptions.SameArrivalDepartureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepositoryJSON flightRepositoryJSON;
    @Autowired
    FlightService(FlightRepositoryJSON flightRepositoryJSON){
        this.flightRepositoryJSON = flightRepositoryJSON;
    }
    public List<Flight> getAllFlights(){
        return flightRepositoryJSON.getAll();
    }

    public Flight addNewFlight(Flight flight ){
        if (validateFlightEntryFields(flight)){
            return flightRepositoryJSON.addEntry(flight);
        }
        return null;
    }

    public Flight editFlight(Flight editedFlight){
        if (validateFlightEntryFields(editedFlight)){
            return this.flightRepositoryJSON.editFlight(editedFlight);
        }
        return null;
    }

    public Flight deleteFlight(String flightId){

        return flightRepositoryJSON.deleteEntryById(flightId);

    }


    private boolean validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            throw new EmptyFieldException("Flight contains empty fields");
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new SameArrivalDepartureException("Flight has same Departure and Arrival | ( " + flight.getDeparture() + ")");
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrival_date() + "T" + flight.getArrival_time());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new InvalidDatesException("Invalid arrival date and time for given departure date and time");
        }

        return true;
    }
}
