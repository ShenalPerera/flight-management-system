package com.fms.flights.controllers;

import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;
import com.fms.flights.services.FlightService;
import com.fms.services.AirportsFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("flights")
public class FlightController {
    private final FlightService flightService;
    private final AirportsFileHandler airportsFileHandler;
    private final Logger logger;

    @Autowired
    FlightController(FlightService flightService, AirportsFileHandler airportsFileHandler) {
        this.flightService = flightService;
        this.airportsFileHandler = airportsFileHandler;
        this.logger = LoggerFactory.getLogger(FlightController.class);
    }

    @GetMapping("/get-flights")
    public List<Flight> getFlights() {
        logger.info("'/flights/get-flights' accessed");
        return flightService.getAllFlights();
    }

    @GetMapping("/get-filtered-flights")
    public List<Flight> getFilteredFlights(SearchFlightDTO searchFlightDTO) {
        logger.info("Accessed the endpoint : searchDTO = {}",searchFlightDTO);
        return flightService.getFilteredFlightsBySearchOptions(searchFlightDTO);
    }

    @GetMapping("/get-airports")
    public ResponseEntity<List<String>> getAirports() {
        logger.info("Accessed get-airports");
        return ResponseEntity.status(200).body(airportsFileHandler.getAirportFromFile());
    }

    @PostMapping("/add-flight")
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight) {
        logger.info("'add-flight' endpoint accessed | {}", flight);
        return ResponseEntity.status(200).body(flightService.addNewFlight(flight));
    }

    @PutMapping("/edit-flight")
    public ResponseEntity<Flight> editFlight(@RequestBody Flight editedFlight) {
        logger.info("Accessed '/flights/edit-flights' - Body : {}", editedFlight);
        return ResponseEntity.status(200).body(flightService.editFlight(editedFlight));


    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<String> deleteFlight(@RequestParam(name = "id") String flightId) {
        logger.info("Accessed '/flights/delete-flights' - FLightId : {}", flightId);
        flightService.deleteFlight(flightId);
        return ResponseEntity.status(200).body(null);
    }
}
