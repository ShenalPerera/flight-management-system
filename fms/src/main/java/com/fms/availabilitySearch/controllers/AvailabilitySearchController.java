package com.fms.availabilitySearch.controllers;

import com.fms.availabilitySearch.models.AvailableFlight;
import com.fms.availabilitySearch.models.AvailableSearch;
import com.fms.availabilitySearch.services.AvailabilitySearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/availability")
public class AvailabilitySearchController {

    private final AvailabilitySearchService availabilitySearchService;
    private final Logger logger;

    @Autowired
    AvailabilitySearchController(AvailabilitySearchService availabilitySearchService) {
        this.availabilitySearchService = availabilitySearchService;
        this.logger = LoggerFactory.getLogger(AvailabilitySearchController.class);
    }

    @GetMapping
    public ResponseEntity<List<AvailableFlight>> getAvailableFlights(AvailableSearch availableSearch) {
        logger.info("'availability' endpoint accessed | {}", availableSearch);
        return new ResponseEntity<>(availabilitySearchService.getAllAvailableFlights(availableSearch), HttpStatus.OK);
    }
}
