package com.fms.routes.services;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.routes.models.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutesService {
    static int UNIQUE_ROUTE_ID;
    List<Route> INITIAL_ROUTES = new ArrayList<>();

    private final Logger logger;
    public RoutesService() {
        INITIAL_ROUTES.add(new Route(1, "sri lanka", "india", 12.4, 2.5));
        INITIAL_ROUTES.add(new Route(2, "usa", "dubai", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(3, "mexico", "germany", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(4, "jordan", "usa", 15.4, 22.5));
        INITIAL_ROUTES.add(new Route(5, "uk", "canada", 15.4, 22.5));

        this.logger = LoggerFactory.getLogger(RoutesService.class);
        UNIQUE_ROUTE_ID = INITIAL_ROUTES.size();
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
            if (r.getDeparture().equalsIgnoreCase(route.getDeparture()) && r.getDestination().equalsIgnoreCase(route.getDestination())) {
                return true;
            }
        }
        return false;
    }

    public void checkEmptyFields(Route route) {
        if (route.getDeparture()==null || route.getDestination()==null || route.getMileage()==0 || route.getDurationH()==0) {
            logger.error("'/api/routes-screen/create-route' accessed with dep->{},des->{},mil->{},hrs->{}",
                    route.getDeparture(), route.getDestination(), route.getMileage(), route.getDurationH());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }

    public void checkDepartureAndDestination(String departure, String destination) {
        if (departure.equals("") || destination.equals("") || departure.equalsIgnoreCase(destination)) {
            logger.error("'/api/routes-screen/update-route' accessed with dep->{},des->{}",
                    departure, destination);
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
    }

    public void checkDuplicates(String departure, String destination) {
        for (Route r : INITIAL_ROUTES) {
            if (r.getDeparture().equalsIgnoreCase(departure) && r.getDestination().equalsIgnoreCase(destination)) {
                logger.error("'/api/routes-screen/create-route' accessed with dep->{},des->{} which are already there",
                        departure, destination);
                throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
            }
        }
    }

    public List<Route> sendAllRoutes() {
        return INITIAL_ROUTES;
    }

    public ResponseEntity<Route> createRoute(Route route) {
        checkEmptyFields(route);
        checkDepartureAndDestination(route.getDeparture(), route.getDestination());
        checkDuplicates(route.getDeparture(), route.getDestination());

        route.setRouteID(++UNIQUE_ROUTE_ID);
        INITIAL_ROUTES.add(route);
        return new ResponseEntity<>(INITIAL_ROUTES.get(INITIAL_ROUTES.size()-1), HttpStatus.CREATED);

    }

    public ResponseEntity<Route> editRoute(Route route) {
        checkEmptyFields(route);
        checkDepartureAndDestination(route.getDeparture(), route.getDestination());

        if (hasConflictWhenUpdating(route)) {
            logger.error("'/api/routes-screen/update-route' accessed with dep->{},des->{} which are already there",
                    route.getDeparture(), route.getDestination());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        for (Route r : INITIAL_ROUTES) {
            if (r.getRouteID() == route.getRouteID()) {
                r = updateTheContent(r, route);
                return new ResponseEntity<>(r, HttpStatus.OK);
            }
        }
        logger.error("'/api/routes-screen/update-route' accessed with routeID->{} which is not found",
                route.getRouteID());
        throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);

    }

    public ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){
        boolean isIdExists = isIdExisting(routeID);

        if (isIdExists) {
            INITIAL_ROUTES.removeIf(route->route.getRouteID()==routeID);
            return new ResponseEntity<>(routeID, HttpStatus.OK);
        } else {
            logger.error("'/api/routes-screen/delete-route' accessed with routeID->{} which is not found",
                    routeID);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
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
