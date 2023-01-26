package com.fms.flights.services;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.flights.models.Flight;
import com.fms.flights.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    @Autowired
    FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }
    public List<Flight> getAllFlights(){
        return flightRepository.getAll();
    }

    public List<Flight> getFilteredFlightsBySearchOptions(String flightNUmber,
                                                          String departure,
                                                          String arrival,
                                                          String departureDate,
                                                          String departureTime,
                                                          String arrivalDate,
                                                          String arrivalTime) {
        Flight searchFlightObject = new Flight(null,flightNUmber,arrival,departure,arrivalDate,departureDate,arrivalTime,departureTime);
        return this.flightRepository.getFLightsByParameters(searchFlightObject);
    }

    public Flight addNewFlight(Flight flight ){
        if (isFlightValid(flight)){
            return flightRepository.addEntry(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight editFlight(Flight flight){
        if (isFlightValid(flight)){
            return flightRepository.editFlight(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight deleteFlight(String flightId){
        Flight flight = flightRepository.deleteEntryById(flightId);
        if (flight == null){
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
        return flight;
    }


    private void validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrival_date() + "T" + flight.getArrival_time());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private boolean isFlightValid(Flight flight){
        validateFlightEntryFields(flight);
        return flightRepository.getFlightsByFlightNumberNDepartureDate(flight).isEmpty();
    }
}
