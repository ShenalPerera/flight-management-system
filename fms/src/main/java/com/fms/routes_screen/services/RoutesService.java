package com.fms.routes_screen.services;

import com.fms.routes_screen.models.Route;

import java.util.ArrayList;
import java.util.List;

public class RoutesService {
    List<Route> INITIAL_ROUTES = new ArrayList<>();

    public RoutesService() {
        INITIAL_ROUTES.add(new Route(1, "Galle", "India", 12.4, 2.5));
        INITIAL_ROUTES.add(new Route(2, "Colombo", "Dubai", 15.4, 22.5));
    }

    public String sayHello() {
        return "Welcome to routes screen";
    }

    public List<Route> sendAllRoutes() {
        return INITIAL_ROUTES;
    }

    public Route createRoute(Route route) {
        INITIAL_ROUTES.add(route);
        return INITIAL_ROUTES.get(INITIAL_ROUTES.size()-1);
    }
}
