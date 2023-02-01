package com.fms.routes.services;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.routes.DAOs.RouteDao;
import com.fms.routes.DAOsImplementations.RouteDaoImpl;
import com.fms.routes.models.Route;
import com.fms.routes.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RoutesService {

    private final Logger logger;
    private final RouteRepository routeRepository;
    private final JdbcTemplate jdbcTemplate;
    private final RouteDao routeDao;

//    String GET_ALL_ROUTES_QUERY = "SELECT * FROM route;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_AND_DESTINATION_QUERY = "SELECT * FROM route WHERE departure = ? AND destination = ?;";
    String GET_FILTERED_ROUTES_BY_DEPARTURE_QUERY = "SELECT * FROM route WHERE departure = ?;";
    String GET_FILTERED_ROUTES_BY_DESTINATION_QUERY = "SELECT * FROM route WHERE destination = ?;";

    @Autowired
    public RoutesService(RouteRepository routeRepository, JdbcTemplate jdbcTemplate, RouteDao routeDao) {
        this.routeRepository = routeRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.logger = LoggerFactory.getLogger(RoutesService.class);
        this.routeDao = routeDao;
    }

    // ******************************************** HELPER METHODS *****************************************************

    public void checkInputFields(Route route) {
        if (route.getDeparture()==null || route.getDestination()==null || route.getMileage()==0 || route.getDurationH()==0) {
            logger.error("'/api/routes-screen/create-route' accessed with dep->{},des->{},mil->{},hrs->{}",
                    route.getDeparture(), route.getDestination(), route.getMileage(), route.getDurationH());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }

        if (route.getDeparture().equals("") || route.getDestination().equals("") || route.getDeparture().equalsIgnoreCase(route.getDestination())
        || route.getMileage()<0 || route.getDurationH()<0) {
            logger.error("'/api/routes-screen/update-route' accessed with dep->{},des->{},mil->{},hrs->{}",
                    route.getDeparture(), route.getDestination(), route.getMileage(), route.getDurationH());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }

    public void checkRouteIDAndDuplicates(Route route, List<Route> routeList) {
        if (routeList.size()>1) {
            logger.error("'/api/routes-screen/update-route' accessed with dep->{},des->{} which are already there",
                    route.getDeparture(), route.getDestination());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        if (routeList.isEmpty() || routeList.get(0).getRouteID() != route.getRouteID()) {
            logger.error("'/api/routes-screen/update-route' accessed with routeID->{} which is not found", route.getRouteID());
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

    // **************************************** ENDPOINTS SERVICES *****************************************************

    public List<Route> sendAllRoutes() {
        return routeDao.getAllRoutes();
    }

    public ResponseEntity<Route> createRoute(Route route) {
        checkInputFields(route);
        try{
            route.setCreatedDateTime(new Timestamp(new Date().getTime()));
            routeRepository.save(route);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("'/api/routes-screen/create-route' accessed with dep->{},des->{} which are already there",
                    route.getDeparture(), route.getDestination());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }


    public ResponseEntity<Route> editRoute(Route route) {
        checkInputFields(route);
        List<Route> routeList = routeRepository.findByDepartureAndDestinationOrRouteID(
                route.getDeparture(), route.getDestination(), route.getRouteID()
        );

        checkRouteIDAndDuplicates(route, routeList);
        route.setModifiedDateTime(new Timestamp(new Date().getTime()));

        try {
            Route updatedRoute = routeRepository.save(route);
            return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("'/api/routes-screen/update-route' accessed with version->{}", route.getVersion());
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }

    }

    public ResponseEntity<Integer> deleteRoute(@RequestParam int routeID){
        try {
            routeRepository.deleteById(routeID);
            return new ResponseEntity<>(routeID, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("'/api/routes-screen/delete-route' accessed with routeID->{} which is not found",
                    routeID);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

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
            return new ResponseEntity<>(sendAllRoutes(), HttpStatus.OK);
        }

    }

}
