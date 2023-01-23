package com.fms.flights.services;

import com.fms.HttpStatusCodesFMS.HttpCodesFMS;
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
        if (isFlightValid(flight)){
            return flightRepositoryJSON.addEntry(flight);
        }
        throw new FMSException(HttpCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight editFlight(Flight flight){
        if (isFlightValid(flight)){
            return flightRepositoryJSON.editFlight(flight);
        }
        throw new FMSException(HttpCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight deleteFlight(String flightId){
        Flight flight = flightRepositoryJSON.deleteEntryById(flightId);
        if (flight == null){
            throw new FMSException(HttpCodesFMS.ENTRY_NOT_FOUND);
        }
        return flight;
    }


    private void validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            throw new FMSException(HttpCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new FMSException(HttpCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrival_date() + "T" + flight.getArrival_time());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new FMSException(HttpCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private boolean isFlightValid(Flight flight){
        validateFlightEntryFields(flight);
        return flightRepositoryJSON.getFlightsByFlightNumberNDepartureDate(flight).isEmpty();
    }
}
