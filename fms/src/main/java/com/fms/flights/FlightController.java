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


    @PutMapping("/edit-flight")
    public ResponseEntity<Object> editFlight(@RequestBody Flight editedFlight){
        if (flightService.editFlight(editedFlight) != null){
            return ResponseEntity.status(200).body("Edit Successful");
        }
        System.out.println("This lib exceeded");
        return ResponseEntity.status(204).body("Entry is not found!");
    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<Object> deleteFlight(@RequestParam(name = "id") String flightId) {
        return flightService.deleteFlight(flightId);
    }
}
