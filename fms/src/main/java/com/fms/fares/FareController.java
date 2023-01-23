package com.fms.fares;

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

    @GetMapping("locations")
    public ResponseEntity<List<String>> getLocations() {
        logger.info("'locations' endpoint accessed");
        return new ResponseEntity<>(service.getLocations(), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<Fare>> getSearchedEntries(
            @RequestParam(value = "departure", defaultValue = "") String departure,
            @RequestParam(value = "arrival", defaultValue = "") String arrival) {
        logger.info("'search' endpoint accessed");
        return new ResponseEntity<>(service.getSearchedEntries(departure, arrival), HttpStatus.OK);
    }

    @PostMapping("entry")
    public ResponseEntity<Fare> createEntry(@RequestBody Fare entry) {
        logger.trace("create 'entry' endpoint accessed");
        return new ResponseEntity<>(service.createEntry(entry), HttpStatus.CREATED);
    }

    @PutMapping("entry")
    public ResponseEntity<Fare> editEntry(@RequestBody Fare entry) {
        logger.info("update 'entry' endpoint accessed");
        return new ResponseEntity<>(service.editEntry(entry), HttpStatus.OK);
    }

    @DeleteMapping("entry")
    public ResponseEntity<Integer> deleteEntry(@RequestParam(value = "id") int id) {
        logger.info("delete 'entry' endpoint accessed");
        return new ResponseEntity<>(service.deleteEntry(id), HttpStatus.NO_CONTENT);
    }
}
