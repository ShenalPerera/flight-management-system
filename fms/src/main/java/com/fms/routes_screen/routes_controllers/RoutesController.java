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
    static List<Route> INITIAL_ROUTES = new ArrayList<>();


//            List<Route> INITIAL_ROUTES = {new Route(1, "Galle", "India", 12.4, 2.5),
//            new Route(2, "Colombo", "Dubai", 15.4, 22.5),};
//    Route[] INITIAL_ROUTES = {
//            new Route(1, "Galle", "India", 12.4, 2.5),
//            new Route(2, "Colombo", "Dubai", 15.4, 22.5),
//    };

//    Map<String, Route[]> allRoutes = new HashMap<>();



    @GetMapping("/api/routes-screen")
    public String sayHello() {

        return routesService.sayHello();
    }

//    @RequestMapping("/api/routes-screen/get-routes")
//    public Route[] sendRoutes() {
//        return INITIAL_ROUTES;
//    }

    @GetMapping("/api/routes-screen/get-routes")
    public List<Route> sendAllRoutes() {
        return routesService.sendAllRoutes();
//        return Arrays.asList(INITIAL_ROUTES);
    }

//    @PostMapping("/api/routes-screen/create-route")
//    public List<Route> createRoute(@RequestBody Route route) {
//
//    }


}
