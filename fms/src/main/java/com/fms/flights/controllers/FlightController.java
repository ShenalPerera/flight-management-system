package com.fms.flights.controllers;

import com.fms.flights.models.Flight;
import com.fms.flights.services.FlightService;
import com.fms.services.AirportsFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<Flight> getFilteredFlights(@RequestParam(name = "flightNumber") String flightNumber,
                                           @RequestParam(name = "departure") String departure,
                                           @RequestParam(name = "arrival") String arrival,
                                           @RequestParam(name = "departureDate") String departureDate,
                                           @RequestParam(name = "departureTime") String departureTime,
                                           @RequestParam(name = "arrivalDate") String arrivalDate,
                                           @RequestParam(name = "arrivalTime") String arrivalTime
                                           ){
        logger.info("'/flights/get-filtered-flights?name={}&departure={}&arrival={}&departureDate={}&departureTime={}&arrivalDate={}&arrivalTime={}' accessed",
                flightNumber,
                departure,
                arrival,
                departureDate,
                departureTime,
                arrivalDate,
                arrivalTime);
        return flightService.getFilteredFlightsBySearchOptions(flightNumber,departure,arrival,departureDate,departureTime,arrivalDate,arrivalTime);
    }
    @GetMapping("/get-airports")
    public ResponseEntity<List<String>>  getAirports(){
        logger.info("'/flights/get-airports' accessed");
        return ResponseEntity.status(200).body(airportsFileHandler.getAirportFromFile());
    }

    @PostMapping("/add-flight")
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight) {
        logger.info("Accessed '/flights/get-flights' - Body : {}",flight);
        return ResponseEntity.status(200).body(flightService.addNewFlight(flight));
    }

    @PutMapping("/edit-flight")
    public ResponseEntity<Flight> editFlight(@RequestBody Flight editedFlight){
        logger.info("Accessed '/flights/edit-flights' - Body : {}",editedFlight);
        return ResponseEntity.status(200).body(flightService.editFlight(editedFlight));



    }

    @DeleteMapping("/delete-flight")
    public ResponseEntity<String> deleteFlight(@RequestParam(name = "id") String flightId) {
        logger.info("Accessed '/flights/delete-flights' - FLightId : {}",flightId);
        flightService.deleteFlight(flightId);
        return  ResponseEntity.status(200).body(null);
    }
}
