package com.fms.routes.repositories;

import com.fms.routes.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    Route findFirstByDepartureAndDestination(String departure, String destination);
    List<Route> findByDepartureAndDestinationOrRouteID(String departure, String destination, int routeID);
    int countAllByDepartureAndDestination(String departure, String destination);



}
