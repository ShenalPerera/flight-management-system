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
        for (Route r : INITIAL_ROUTES) {
            if (r.getDeparture().equals(route.getDeparture()) && route.getDestination().equals(route.getDestination())) {
                return null;
            }
        }
        INITIAL_ROUTES.add(route);
        return INITIAL_ROUTES.get(INITIAL_ROUTES.size()-1);
    }

    public Route editRoute(Route route) {
        for (Route r : INITIAL_ROUTES) {
            if (r.getRouteID() == route.getRouteID()) {
                r.setDeparture(route.getDeparture());
                r.setDestination(route.getDestination());
                r.setMileage(route.getMileage());
                r.setDurationH(route.getDurationH());

                return r;
            }
        }
        return null;
    }
}
