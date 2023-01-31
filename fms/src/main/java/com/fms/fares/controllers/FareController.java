package com.fms.fares.controllers;

import com.fms.fares.services.FareService;
import com.fms.fares.models.Fare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/fares")
public class FareController {
    private final FareService service;
    private final Logger logger;

    @Autowired
    FareController(FareService service) {
        this.service = service;
        this.logger = LoggerFactory.getLogger(FareController.class);
    }

    @GetMapping("search")
    public ResponseEntity<List<Fare>> getSearchedEntries(
            @RequestParam(value = "departure", defaultValue = "") String departure,
            @RequestParam(value = "arrival", defaultValue = "") String arrival) {
        logger.info("'search' endpoint accessed");
        return new ResponseEntity<>(service.getSearchedFares(departure, arrival), HttpStatus.OK);
    }

    @PostMapping("fare")
    public ResponseEntity<Fare> createEntry(@RequestBody Fare fare) {
        logger.info("create 'fare' endpoint accessed");
        return new ResponseEntity<>(service.createFare(fare), HttpStatus.CREATED);
    }

    @PutMapping("fare")
    public ResponseEntity<Fare> editEntry(@RequestBody Fare fare) {
        logger.info("update 'fare' endpoint accessed");
        return new ResponseEntity<>(service.editFare(fare), HttpStatus.OK);
    }

    @DeleteMapping("fare")
    public ResponseEntity<Integer> deleteEntry(@RequestParam(value = "id") int id) {
        logger.info("delete 'fare' endpoint accessed");
        return new ResponseEntity<>(service.deleteFare(id), HttpStatus.NO_CONTENT);
    }
}
