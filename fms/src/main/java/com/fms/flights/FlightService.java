package com.fms.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Flight editFlight(Flight editedFlight){
        return this.flightRepositoryJSON.editFlight(editedFlight);
    }

    public ResponseEntity<Object> deleteFlight(String flightId){
        Flight deletedFlight = flightRepositoryJSON.deleteEntryById(flightId);

        if (deletedFlight == null) {
            return ResponseEntity.status(207).body("Entry not found!");
        }
        return new ResponseEntity<>(deletedFlight, HttpStatus.OK);

    }
}
