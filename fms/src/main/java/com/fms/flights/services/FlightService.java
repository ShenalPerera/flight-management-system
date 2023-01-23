package com.fms.flights.services;

import com.fms.HttpCodesFMS.HttpCodesFMS;
import com.fms.exceptions.FMSException;
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
        validateFlightEntryFields(flight);

        List<Flight> flightsOnSameDay = flightRepositoryJSON.getFlightsByFlightNumberNDepartureDate(flight);
        Flight newFlight =  flightRepositoryJSON.addEntry(flight);

        if (newFlight == null){
            throw new FMSException(HttpCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        return newFlight;


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
            throw new FMSException(HttpCodesFMS.EMPTY_FILED_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new FMSException(HttpCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrival_date() + "T" + flight.getArrival_time());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new FMSException(HttpCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }

        return true;
    }
}
