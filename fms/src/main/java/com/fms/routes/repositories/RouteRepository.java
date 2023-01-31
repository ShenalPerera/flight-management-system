package com.fms.routes.repositories;

import com.fms.routes.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findByDepartureAndDestinationOrRouteID(String departure, String destination, int routeID);

}
