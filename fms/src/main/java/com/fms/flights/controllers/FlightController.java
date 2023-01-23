package com.fms.flights.controllers;

import com.fms.flights.DTOs.Flight;
import com.fms.flights.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("flights")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/get-flights")
    public List<Flight> getFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping("/add-flight")
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight) {
        return ResponseEntity.status(200).body(flightService.addNewFlight(flight));
    }


    @PutMapping("/edit-flight")
    public ResponseEntity<Object> editFlight(@RequestBody Flight editedFlight){
        Flight newFlight = flightService.editFlight(editedFlight);

        if (newFlight != null){
            return ResponseEntity.status(200).body(newFlight);
        }
        return ResponseEntity.status(422).body("Form submission Failed!");
    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<Object> deleteFlight(@RequestParam(name = "id") String flightId) {
        Flight deletedFlight = flightService.deleteFlight(flightId);
        if (deletedFlight == null) {
            return ResponseEntity.status(409).body("Entry not found!");
        }
        return new ResponseEntity<>(deletedFlight, HttpStatus.OK);
    }
}
