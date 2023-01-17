package com.fms.routes_screen.routes_controllers;

import com.fms.routes_screen.models.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutesController {

    Route[] INITIAL_ROUTES = {
            new Route(1, "Galle", "India", 12.4, 2.5),
            new Route(2, "Colombo", "Dubai", 15.4, 22.5),
    };

    @RequestMapping("/api/routes-screen")
    public String sayHello() {
        return "Welcome to routes screen";
    }

//    @RequestMapping("/api/routes-screen/get-routes")
//    public Route[] sendRoutes() {
//        return INITIAL_ROUTES;
//    }
}
