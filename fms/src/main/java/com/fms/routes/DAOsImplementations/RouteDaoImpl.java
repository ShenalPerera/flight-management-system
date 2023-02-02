package com.fms.routes.DAOsImplementations;

import com.fms.exceptions.FMSException;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.routes.DAOs.RouteDao;
import com.fms.routes.models.Route;
import com.fms.routes.services.RoutesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger;

    String GET_ALL_ROUTES_QUERY = "SELECT * FROM route;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY = "SELECT * FROM route WHERE departure = ? AND destination = ?;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY = "SELECT * FROM route WHERE departure = ?;";
    String GET_FILTERED_ROUTES_BY_DESTINATION_QUERY = "SELECT * FROM route WHERE destination = ?;";
    String GET_NO_OF_LOCATIONS_COMBINATIONS_IN_FARES = "SELECT COUNT(*) FROM fare WHERE fare.departure = ? AND fare.arrival = ?;";

    @Autowired
    public RouteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = LoggerFactory.getLogger(RouteDaoImpl.class);
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
                        rs.getInt("version")
                )));
    }

    @Override
    public ResponseEntity<List<Route>> searchRoutes(String departure, String destination) {
        RowMapper<Route> rowMapper = (resultSet, rowNum)-> new Route(
                resultSet.getInt("routeID"),
                resultSet.getString("departure"),
                resultSet.getString("destination"),
                resultSet.getDouble("mileage"),
                resultSet.getDouble("durationH"),
                resultSet.getTimestamp("created_date_time"),
                resultSet.getTimestamp("modified_date_time"),
                resultSet.getInt("version")
        );
        List<Route> filteredRoutes;
        if (!departure.isEmpty() && destination.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY, rowMapper, departure);
            return new ResponseEntity<>(filteredRoutes, HttpStatus.OK);
        } else if (departure.isEmpty() && !destination.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DESTINATION_QUERY, rowMapper, destination);
            return new ResponseEntity<>(filteredRoutes, HttpStatus.OK);
        } else if (!departure.isEmpty()) {
            filteredRoutes = jdbcTemplate.query(this.GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY, rowMapper, departure, destination);
            return new ResponseEntity<>(filteredRoutes, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(getAllRoutes(), HttpStatus.OK);
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
        return 0;
    }
}
