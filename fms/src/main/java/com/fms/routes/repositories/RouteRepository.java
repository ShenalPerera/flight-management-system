package com.fms.routes.repositories;

import com.fms.routes.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    Route findFirstByDepartureAndDestinationAndRouteIDNot(String departure, String destination, int routeID);
    Route findFirstByDepartureAndDestination(String departure, String destination);
    Route findByRouteID(int routeID);
}
