package com.fms.routes_screen.routes_controllers;

import com.fms.routes_screen.models.Route;
import com.fms.routes_screen.services.RoutesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoutesController {

    RoutesService routesService = new RoutesService();

    @GetMapping("/api/routes-screen")
    public String sayHello() {
        return routesService.sayHello();
    }

    @GetMapping("/api/routes-screen/get-routes")
    ResponseEntity<List<Route>> sendAllRoutes() {
        return new ResponseEntity<>(routesService.sendAllRoutes(), HttpStatus.OK);
    }

    @PostMapping("/api/routes-screen/create-route")
    ResponseEntity<Route> createRoute(@RequestBody Route route) {
        return routesService.createRoute(route);
    }

    @PutMapping("/api/routes-screen/update-route")
    ResponseEntity<Route> editRoute(@RequestBody Route route) {
        return routesService.editRoute(route);
    }

    @DeleteMapping("/api/routes-screen/delete-route")
    ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){
        return routesService.deleteRoute(routeID);
    }

    @GetMapping("/api/routes-screen/search-routes")
    ResponseEntity<List<Route>> searchRoutes(@RequestParam String departure, @RequestParam String destination) {
        return routesService.searchRoutes(departure, destination);
    }

}
