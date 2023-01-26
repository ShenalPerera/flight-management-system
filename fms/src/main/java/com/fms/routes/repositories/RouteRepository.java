package com.fms.routes.repositories;

import com.fms.routes.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    Route findFirstByDepartureAndDestinationAndRouteIDNot(String departure, String destination, int routeID);

}
