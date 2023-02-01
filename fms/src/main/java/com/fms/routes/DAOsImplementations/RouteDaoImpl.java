package com.fms.routes.DAOsImplementations;

import com.fms.routes.DAOs.RouteDao;
import com.fms.routes.models.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate jdbcTemplate;

    String GET_ALL_ROUTES_QUERY = "SELECT * FROM route;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY = "SELECT * FROM route WHERE departure = ? AND destination = ?;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY = "SELECT * FROM route WHERE departure = ?;";
    String GET_FILTERED_ROUTES_BY_DESTINATION_QUERY = "SELECT * FROM route WHERE destination = ?;";

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
                        rs.getInt("version")
                )));
    }
}
