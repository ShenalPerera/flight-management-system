package com.flightmanagementsystem.backend.routesscreen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoutesScreenController {

    final String base = "/routes-screen";
    @RequestMapping(value = base, method = RequestMethod.GET)
    public String routeScreenHello() {
        return "Welcome to route screen.";
    }

//    @RequestMapping(value = base+"/get-routes", method = RequestMethod.GET)
//    public String sendRoutes() {
//
//    }
}
