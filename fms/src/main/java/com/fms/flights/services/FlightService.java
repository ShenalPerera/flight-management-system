package com.fms.flights.services;

import com.fms.flights.DTOs.Flight;
import com.fms.flights.FlightRepositoryJSON;
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
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrival_date() + "T" + flight.getArrival_time());
        return !(departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime) || !flight.isValidFlight());
    }
}
