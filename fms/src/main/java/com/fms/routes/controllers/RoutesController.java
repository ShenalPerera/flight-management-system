package com.fms.routes.controllers;

import com.fms.routes.models.Route;
import com.fms.routes.services.RoutesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoutesController {
    private final RoutesService routesService;
    private final Logger logger;
    private final String routesBaseUrl = "/api/routes-screen";

    @Autowired
    RoutesController(RoutesService routesService) {
        this.routesService = routesService;
        this.logger = LoggerFactory.getLogger(RoutesController.class);
    }

    @GetMapping(routesBaseUrl + "/get-routes")
    ResponseEntity<List<Route>> sendAllRoutes() {
        logger.info("controller[sendAllRoutes]");
        return new ResponseEntity<>(routesService.sendAllRoutes(), HttpStatus.OK);
    }

    @PostMapping(routesBaseUrl + "/create-route")
    ResponseEntity<Route> createRoute(@RequestBody Route route) {
        logger.info("controller[createRoute] {}", route);
        return new ResponseEntity<>(routesService.createRoute(route), HttpStatus.OK);
    }

    @PutMapping(routesBaseUrl + "/update-route")
    ResponseEntity<Route> editRoute(@RequestBody Route route) {
        logger.info("controller[editRoute] {}", route);
        return new ResponseEntity<>(routesService.editRoute(route), HttpStatus.OK);
    }

    @DeleteMapping(routesBaseUrl + "/delete-route")
    ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){
        logger.info("controller[deleteRoute] id->{}", routeID);
        return new ResponseEntity<>(routesService.deleteRoute(routeID), HttpStatus.OK);
    }

    @GetMapping(routesBaseUrl + "/search-routes")
    ResponseEntity<List<Route>> searchRoutes(@RequestParam String departure, @RequestParam String destination) {
        logger.info("controller[searchRoutes] dep->{} des->{}", departure, destination);
        return new ResponseEntity<>(routesService.searchRoutes(departure, destination), HttpStatus.OK);
    }

}
