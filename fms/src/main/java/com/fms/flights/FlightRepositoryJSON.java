package com.fms.flights;

import com.fms.flights.DTOs.Flight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FlightRepositoryJSON {

    private final List<Flight> flights = new ArrayList<>();

    FlightRepositoryJSON(){
        flights.add(new Flight("1", "1", "colombo", "India", "2023-01-04", "13:04", "2023-01-05", "11:00"));
    }


    public List<Flight> getAll(){
        return flights;
    }

    public Flight addEntry(Flight flight){
        flights.add(flight);
        return flight;
    }

    public Flight deleteEntryById(String id){
        Flight deletedFlight = flights.stream().filter(flight -> Objects.equals(flight.getId(), id)).findFirst().orElse(null);
        boolean isDeleted = flights.remove(deletedFlight);

        if (isDeleted){
            return deletedFlight;
        }
        return null;
    }

    public Flight editFlight(Flight editedFlight){
        try {
            return flights.set(flights.indexOf(editedFlight),editedFlight );
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    public List<Flight> getFlightsByFlightNumberNDepartureDate(Flight newtFlight){
        return flights.stream().filter(flightEntry ->
                Objects.equals(flightEntry.getFlight_number(), newtFlight.getFlight_number())).
                collect(Collectors.toList());
    }
}
