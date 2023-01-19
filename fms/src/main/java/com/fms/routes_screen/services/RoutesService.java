package com.fms.routes_screen.services;

import com.fms.routes_screen.models.Route;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoutesService {
    static int UNIQUE_ROUTE_ID;
    List<Route> INITIAL_ROUTES = new ArrayList<>();

    public RoutesService() {
        INITIAL_ROUTES.add(new Route(1, "galle", "india", 12.4, 2.5));
        INITIAL_ROUTES.add(new Route(2, "colombo", "dubai", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(3, "maxico", "quatar", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(4, "jordan", "usa", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(5, "uk", "canada", 15.4, 22.5));

        UNIQUE_ROUTE_ID = INITIAL_ROUTES.size();
    }

    public String sayHello() {
        return "Welcome to routes screen";
    }

    public List<Route> sendAllRoutes() {
        return INITIAL_ROUTES;
    }

    public Route createRoute(Route route) {
        for (Route r : INITIAL_ROUTES) {
            if (r.getDeparture().equals(route.getDeparture()) && r.getDestination().equals(route.getDestination())) {
                return null;
            }
        }
        route.setRouteID(++UNIQUE_ROUTE_ID);
        INITIAL_ROUTES.add(route);
        return INITIAL_ROUTES.get(INITIAL_ROUTES.size()-1);
    }

    public Route editRoute(Route route) {

        // you can optimize
        for (Route r : INITIAL_ROUTES) {
            if (r.getDeparture().equals(route.getDeparture()) && r.getDestination().equals(route.getDestination())) {
                return null;
            }
        }

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

    public int deleteRoute(@RequestParam int routeID){
        INITIAL_ROUTES.removeIf(route->route.getRouteID()==routeID);
        return routeID;
    }

    public List<Route> searchRoutes(String departure, String destination) {

//        return this.ALL_ROUTES.filter(
//                x => (searchFormDeparture === "" || searchFormDeparture === x.departure)
//                && (searchFormDestination === "" || searchFormDestination === x.destination))
        return INITIAL_ROUTES.stream()
                .filter(
                        route->(departure.equals("") || departure.equals(route.getDeparture())) &&
                                (destination.equals("") || destination.equals(route.getDestination())))
                .collect(Collectors.toList());
    }



}
