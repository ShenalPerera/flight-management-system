package com.fms.routes.DAOs;

import com.fms.routes.models.Route;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteDao {
    public List<Route> getAllRoutes();
    public ResponseEntity<List<Route>> searchRoutes(String departure, String destination);
    public int searchNumOfLocationsCombinationInFares(String departure, String destination);
    public int searchNumOfLocationsCombinationInFlights(String departure, String destination);
}
