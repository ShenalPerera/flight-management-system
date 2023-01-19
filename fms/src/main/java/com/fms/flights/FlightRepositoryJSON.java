package com.fms.flights;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
}
