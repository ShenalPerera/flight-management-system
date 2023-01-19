package com.fms.flights;

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
    public Flight addNewFlight(@RequestBody Flight flight) {

        return flightService.addNewFlight(flight);
    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<Flight> deleteFlight(@RequestParam(name = "id") String flightId) {
        Flight deletedFlight = flightService.deleteFlight(flightId);

        if (deletedFlight == null) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedFlight, HttpStatus.OK);
    }
}
