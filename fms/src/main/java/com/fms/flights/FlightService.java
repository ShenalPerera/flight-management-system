package com.fms.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return flightRepositoryJSON.addEntry(flight);
    }

    public Flight deleteFlight(String flightId){
        return flightRepositoryJSON.deleteEntryById(flightId);
    }
}
