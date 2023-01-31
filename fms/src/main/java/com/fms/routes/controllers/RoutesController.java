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
        logger.info("'/api/routes-screen/get-routes' accessed");
        return new ResponseEntity<>(routesService.sendAllRoutes(), HttpStatus.OK);
    }

    @PostMapping(routesBaseUrl + "/create-route")
    ResponseEntity<Route> createRoute(@RequestBody Route route) {
        logger.info("'/api/routes-screen/create-route' accessed with dep->{},des->{},mil->{},hrs->{}",
                route.getDeparture(), route.getDestination(), route.getMileage(), route.getDurationH());
        return routesService.createRoute(route);
    }

    @PutMapping(routesBaseUrl + "/update-route")
    ResponseEntity<Route> editRoute(@RequestBody Route route) {
        logger.info("'/api/routes-screen/update-route' accessed with dep->{},des->{},mil->{},hrs->{}",
                route.getDeparture(), route.getDestination(), route.getMileage(), route.getDurationH());
        return routesService.editRoute(route);
    }

    @DeleteMapping(routesBaseUrl + "/delete-route")
    ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){
        logger.info("'/api/routes-screen/delete-route' accessed with routeID->{}", routeID);
        return routesService.deleteRoute(routeID);
    }

    @GetMapping(routesBaseUrl + "/search-routes")
    ResponseEntity<List<Route>> searchRoutes(@RequestParam String departure, @RequestParam String destination) {
        logger.info("'/api/routes-screen/search-routes' accessed with dep->{}, des->{}", departure, destination);
        return routesService.searchRoutes(departure, destination);
    }

}
