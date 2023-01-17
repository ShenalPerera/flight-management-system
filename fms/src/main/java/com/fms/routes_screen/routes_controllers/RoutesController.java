package com.fms.routes_screen.routes_controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fms.routes_screen.models.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoutesController {

    Route[] INITIAL_ROUTES = {
            new Route(1, "Galle", "India", 12.4, 2.5),
            new Route(2, "Colombo", "Dubai", 15.4, 22.5),
    };

//    Map<String, Route[]> allRoutes = new HashMap<>();

    @RequestMapping("/api/routes-screen")
    public String sayHello() {
        return "Welcome to routes screen";
    }

//    @RequestMapping("/api/routes-screen/get-routes")
//    public Route[] sendRoutes() {
//        return INITIAL_ROUTES;
//    }

    @RequestMapping("/api/routes-screen/get-routes")
    public List<Route> sendAllRoutes() {
        return Arrays.asList(INITIAL_ROUTES);
    }
}
