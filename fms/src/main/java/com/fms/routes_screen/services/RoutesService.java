package com.fms.routes_screen.services;

import com.fms.routes_screen.models.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public boolean correctDepartureAndDestination(String departure, String destination) {
        boolean areSame = false;
        if (departure.equals("") || destination.equals("") ||
                departure.equalsIgnoreCase(destination)) {
            areSame = true;
        }
        return areSame;
    }

    public boolean isIdExisting(int routeID) {
        for (Route r : INITIAL_ROUTES) {
            if (r.getRouteID() == routeID) {
                return true;
            }
        }
        return false;
    }

    public Route updateTheContent(Route oldRoute, Route newRoute) {
        oldRoute.setDeparture(newRoute.getDeparture());
        oldRoute.setDestination(newRoute.getDestination());
        oldRoute.setMileage(newRoute.getMileage());
        oldRoute.setDurationH(newRoute.getDurationH());

        return oldRoute;
    }

    public boolean hasConflictWhenUpdating(Route route) {
        for (Route r : INITIAL_ROUTES) {
            if (r.getRouteID() == route.getRouteID()) {
                continue;
            }
            if (r.getDeparture().equals(route.getDeparture()) && r.getDestination().equals(route.getDestination())) {
                return true;
            }
        }
        return false;
    }

    public List<Route> sendAllRoutes() {
        return INITIAL_ROUTES;
    }

    public ResponseEntity<Route> createRoute(Route route) {

        if (correctDepartureAndDestination(route.getDeparture(), route.getDestination())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            for (Route r : INITIAL_ROUTES) {
                if (r.getDeparture().equals(route.getDeparture()) && r.getDestination().equals(route.getDestination())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            route.setRouteID(++UNIQUE_ROUTE_ID);
            INITIAL_ROUTES.add(route);
            return new ResponseEntity<>(INITIAL_ROUTES.get(INITIAL_ROUTES.size()-1), HttpStatus.CREATED);
        }

    }

    public ResponseEntity<Route> editRoute(Route route) {
        if (correctDepartureAndDestination(route.getDeparture(), route.getDestination())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            if (hasConflictWhenUpdating(route)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            for (Route r : INITIAL_ROUTES) {
                if (r.getRouteID() == route.getRouteID()) {
                    r = updateTheContent(r, route);
                    return new ResponseEntity<>(r, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){

        boolean isIdExists = isIdExisting(routeID);

        if (isIdExists) {
            INITIAL_ROUTES.removeIf(route->route.getRouteID()==routeID);
            return new ResponseEntity<Integer>(routeID, HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Route>> searchRoutes(String departure, String destination) {

        return new ResponseEntity<>(
                INITIAL_ROUTES.stream()
                .filter(
                        route->(departure.equals("") || departure.equals(route.getDeparture())) &&
                                (destination.equals("") || destination.equals(route.getDestination())))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

}
