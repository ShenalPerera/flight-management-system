package com.fms.routes.daosImplementations;

import com.fms.routes.daos.RouteDao;
import com.fms.routes.models.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate jdbcTemplate;

    String GET_ALL_ROUTES_QUERY = "SELECT * FROM route WHERE route.status=1 ORDER BY modified_date_time DESC;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY = "SELECT * FROM route WHERE departure = ? AND destination = ? AND route.status=1 ORDER BY modified_date_time DESC;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY = "SELECT * FROM route WHERE departure = ? AND route.status=1 ORDER BY modified_date_time DESC;";
    String GET_FILTERED_ROUTES_BY_DESTINATION_QUERY = "SELECT * FROM route WHERE destination = ? AND route.status=1 ORDER BY modified_date_time DESC;";
    String GET_NO_OF_LOCATIONS_COMBINATIONS_IN_FARES = "SELECT COUNT(*) FROM fare WHERE fare.departure = ? AND fare.arrival = ?;";
    String GET_NO_OF_LOCATIONS_COMBINATIONS_IN_FLIGHTS = "SELECT COUNT(*) FROM flight WHERE flight.departure = ? AND flight.arrival = ?;";

    @Autowired
    public RouteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Route> getAllRoutes() {
        return jdbcTemplate.query(this.GET_ALL_ROUTES_QUERY,
                ((rs, rowNum) -> new Route(
                        rs.getInt("routeID"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getDouble("mileage"),
                        rs.getDouble("durationH"),
                        rs.getTimestamp("created_date_time"),
                        rs.getTimestamp("modified_date_time"),
                        rs.getInt("status"),
                        rs.getInt("version")
                )));
    }

    @Override
    public List<Route> searchRoutes(String departure, String destination) {
        RowMapper<Route> rowMapper = (resultSet, rowNum)-> new Route(
                resultSet.getInt("routeID"),
                resultSet.getString("departure"),
                resultSet.getString("destination"),
                resultSet.getDouble("mileage"),
                resultSet.getDouble("durationH"),
                resultSet.getTimestamp("created_date_time"),
                resultSet.getTimestamp("modified_date_time"),
                resultSet.getInt("status"),
                resultSet.getInt("version")
        );
        List<Route> filteredRoutes;
        if (!departure.isEmpty() && destination.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY, rowMapper, departure);
            return filteredRoutes;
        } else if (departure.isEmpty() && !destination.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DESTINATION_QUERY, rowMapper, destination);
            return filteredRoutes;
        } else if (!departure.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY, rowMapper, departure, destination);
            return filteredRoutes;
        } else{
            return getAllRoutes();
        }
    }

    @Override
    public int searchNumOfLocationsCombinationInFares(String departure, String destination) {
        if (departure != null || destination != null) {
            return jdbcTemplate.queryForObject(this.GET_NO_OF_LOCATIONS_COMBINATIONS_IN_FARES, Integer.class, departure, destination);
        } else {
            return 0;
        }
    }

    @Override
    public int searchNumOfLocationsCombinationInFlights(String departure, String destination) {
        if (departure != null || destination != null) {
            return jdbcTemplate.queryForObject(this.GET_NO_OF_LOCATIONS_COMBINATIONS_IN_FLIGHTS, Integer.class, departure, destination);
        } else {
            return 0;
        }
    }
}
