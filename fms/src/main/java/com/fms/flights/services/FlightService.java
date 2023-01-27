package com.fms.flights.services;

import com.fms.flights.FlightRepositoryFMS;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.flights.models.Flight;
import com.fms.flights.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightRepositoryFMS flightRepositoryFMS;
    @Autowired
    FlightService(FlightRepository flightRepository, FlightRepositoryFMS flightRepositoryFMS){
        this.flightRepository = flightRepository;
        this.flightRepositoryFMS = flightRepositoryFMS;
    }
    public List<Flight> getAllFlights(){
        return flightRepositoryFMS.findAll();
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
        if (isFlightValidForCreate(flight)){
            return flightRepositoryFMS.save(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight editFlight(Flight flight){
        if (isFlightValidForEdit(flight)){
            return flightRepositoryFMS.save(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public boolean deleteFlight(String flightId){
        try {
            flightRepositoryFMS.deleteById(flightId);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }


    private void validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() +"T" + flight.getDepartureTime());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrivalDate() + "T" + flight.getArrivalTime());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private boolean isFlightValidForEdit(Flight flight){

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDateOrId(
                flight.getFlightNumber(),
                flight.getDepartureDate(),
                flight.getId()
        );
        return filteredFlights.size() == 1 && Objects.equals(filteredFlights.remove(0).getId(), flight.getId());
    }

    private boolean isFlightValidForCreate(Flight flight){
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDate(
                flight.getFlightNumber(),
                flight.getDepartureDate()
        );

        return filteredFlights.isEmpty();
    }
}
