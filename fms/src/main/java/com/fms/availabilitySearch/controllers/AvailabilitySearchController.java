package com.fms.availabilitySearch.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/availability")
public class AvailabilitySearchController {

    private final Logger logger;

    @Autowired
    AvailabilitySearchController() {
        this.logger = LoggerFactory.getLogger(AvailabilitySearchController.class);
    }

    @GetMapping
    public ResponseEntity<?> getAvailableFlights() {
        logger.info("'availability' endpoint accessed");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}