package com.fms.routes.daos;

import com.fms.routes.models.Route;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteDao {
    List<Route> getAllRoutes();
    ResponseEntity<List<Route>> searchRoutes(String departure, String destination);
    int searchNumOfLocationsCombinationInFares(String departure, String destination);
    int searchNumOfLocationsCombinationInFlights(String departure, String destination);
}
