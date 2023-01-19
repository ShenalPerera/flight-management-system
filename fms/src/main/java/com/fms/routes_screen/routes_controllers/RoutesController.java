package com.fms.routes_screen.routes_controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fms.routes_screen.models.Route;
import com.fms.routes_screen.services.RoutesService;
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
    public List<Route> sendAllRoutes() {
        return routesService.sendAllRoutes();
    }

    @PostMapping("/api/routes-screen/create-route")
    public Route createRoute(@RequestBody Route route) {
        return routesService.createRoute(route);
    }

    @PutMapping("/api/routes-screen/update-route")
    public Route editRoute(@RequestBody Route route) {
        return routesService.editRoute(route);
    }

    @PostMapping("/api/routes-screen/delete-route")
    public Map<String, Integer> deleteRoute(@RequestBody Route route) {
        return routesService.deleteRoute(route);
    }


}
