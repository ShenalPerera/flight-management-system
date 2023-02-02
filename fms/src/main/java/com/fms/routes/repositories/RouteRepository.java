package com.fms.routes.repositories;

import com.fms.routes.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    Route findByRouteID(int routeID);
    boolean existsRouteByDepartureAndDestination(String departure, String destination);
    Route findByDepartureAndDestination(String departure, String destination);

}
