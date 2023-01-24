package com.fms.flights.controllers;

import com.fms.flights.models.Flight;
import com.fms.flights.services.FlightService;
import com.fms.services.AirportsFileHandler;
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
    private final AirportsFileHandler airportsFileHandler;
    @Autowired
    FlightController(FlightService flightService, AirportsFileHandler airportsFileHandler) {
        this.flightService = flightService;
        this.airportsFileHandler = airportsFileHandler;
    }

    @GetMapping("/get-flights")
    public List<Flight> getFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/get-airports")
    public ResponseEntity<List<String>>  getAirports(){
        return ResponseEntity.status(200).body(airportsFileHandler.getAirportFromFile());
    }

    @PostMapping("/add-flight")
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight) {
        return ResponseEntity.status(200).body(flightService.addNewFlight(flight));
    }

    @PutMapping("/edit-flight")
    public ResponseEntity<Flight> editFlight(@RequestBody Flight editedFlight){
        return ResponseEntity.status(200).body(flightService.editFlight(editedFlight));
    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<Object> deleteFlight(@RequestParam(name = "id") String flightId) {
        return new ResponseEntity<>(flightService.deleteFlight(flightId), HttpStatus.OK);
    }
}
