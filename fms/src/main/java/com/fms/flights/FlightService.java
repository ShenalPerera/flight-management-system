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

    public ResponseEntity<Flight> editFlight(Flight editedFlight){
        Flight updatedFlight = this.flightRepositoryJSON.editFlight(editedFlight);

        if (updatedFlight != null){
            return new ResponseEntity<>(updatedFlight,HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Flight> deleteFlight(String flightId){
        Flight deletedFlight = flightRepositoryJSON.deleteEntryById(flightId);

        if (deletedFlight == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedFlight, HttpStatus.OK);

    }
}
