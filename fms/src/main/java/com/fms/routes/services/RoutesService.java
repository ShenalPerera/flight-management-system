package com.fms.routes.services;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.routes.DAOs.RouteDao;
import com.fms.routes.models.Route;
import com.fms.routes.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoutesService {

    private final Logger logger;
    private final RouteRepository routeRepository;
    private final RouteDao routeDao;

    @Autowired
    public RoutesService(RouteRepository routeRepository, RouteDao routeDao) {
        this.routeRepository = routeRepository;
        this.logger = LoggerFactory.getLogger(RoutesService.class);
        this.routeDao = routeDao;
    }

    // **************************************** ENDPOINTS SERVICES *****************************************************

    public List<Route> sendAllRoutes() {
        return routeDao.getAllRoutes();
    }

    public ResponseEntity<Route> createRoute(Route route) {
        checkInputFields(route);
        try{
            Route toBeActiveRoute = routeRepository.findByDepartureAndDestination(route.getDeparture(), route.getDestination());
            if (toBeActiveRoute == null) {
                route.setStatus(1);
                route.setCreatedDateTime(new Timestamp(new Date().getTime()));
                routeRepository.save(route);
                logger.info("service[createRoute](new) {}", route);
            } else {
                toBeActiveRoute.setStatus(1);
                toBeActiveRoute.setCreatedDateTime(new Timestamp(new Date().getTime()));
                routeRepository.save(toBeActiveRoute);
                logger.info("service[createRoute](activate) {}", route);

            }
//            route.setCreatedDateTime(new Timestamp(new Date().getTime()));
//            route.set
//            routeRepository.save(route);
            logger.info("service[createRoute] {}", route);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("service[createRoute](duplicate) {}", route);
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }


    public ResponseEntity<Route> editRoute(Route route) {
        checkInputFields(route);

        Route routeToBeUpdated = routeRepository.findByRouteID(route.getRouteID());
        if (routeToBeUpdated == null) {
            logger.error("service[checkRouteIDAndDuplicates](idNotFound) {}", route);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }

        try {
            route.setCreatedDateTime(routeToBeUpdated.getCreatedDateTime());
            route.setModifiedDateTime(new Timestamp(new Date().getTime()));
            Route updatedRoute = routeRepository.save(route);
            logger.info("service[edit] {}", route);
            return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("service[edit](dirtyUpdate) {}", route);
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }


//        checkInputFields(route);
//        List<Route> routeList = routeRepository.findByDepartureAndDestinationOrRouteID(
//                route.getDeparture(), route.getDestination(), route.getRouteID()
//        );
//
//        checkRouteIDAndDuplicates(route, routeList);
//        route.setModifiedDateTime(new Timestamp(new Date().getTime()));
//
//        try {
//            Route updatedRoute = routeRepository.save(route);
//            return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
//        } catch (ObjectOptimisticLockingFailureException e) {
//            logger.error("service[edit] {}", route);
//            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
//        }

    }

    public ResponseEntity<Integer> deleteRoute(int routeID){

//        try {
//            System.out.println(routeID);
            Route routeToBeDeleted = routeRepository.findByRouteID(routeID);
            if (routeToBeDeleted == null) {
                logger.error("service[deleteRoute] id->{}", routeID);
                throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
            }

            boolean t = checkToDeleteRoute(routeToBeDeleted);

            if (t) {
                throw new FMSException(HttpStatusCodesFMS.CANNOT_BE_EXECUTED);
            }

//            routeRepository.deleteById(routeID);
            routeToBeDeleted.setStatus(0);
            routeRepository.save(routeToBeDeleted);
            return new ResponseEntity<>(routeID, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("service[deleteRoute] id->{}", routeID);
//            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
//        }




    }

    public boolean checkToDeleteRoute(Route routeToBeDeleted){
        List<Integer> combinationsWithRoute = new ArrayList<>();
        combinationsWithRoute.add(getNumOfCombinationsInFares(routeToBeDeleted.getDeparture(), routeToBeDeleted.getDestination()));
        combinationsWithRoute.add(getNumOfCombinationsInFlights(routeToBeDeleted.getDeparture(), routeToBeDeleted.getDestination()));

        return (combinationsWithRoute.get(0)>0 || combinationsWithRoute.get(1)>0);
    }

    public ResponseEntity<List<Route>> searchRoutes(String departure, String destination) {
        return routeDao.searchRoutes(departure, destination);

    }

    // ******************************************** HELPER METHODS *****************************************************

    public int getNumOfCombinationsInFares(String departure, String destination) {
        return routeDao.searchNumOfLocationsCombinationInFares(departure, destination);
    }

    public int getNumOfCombinationsInFlights(String departure, String destination) {
        return routeDao.searchNumOfLocationsCombinationInFlights(departure, destination);
    }

    public void checkInputFields(Route route) {
        if (route.getDeparture()==null || route.getDestination()==null || route.getMileage()==0 || route.getDurationH()==0) {
            logger.error("service[checkInputFields](emptyFields) {}", route);
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }

        if (route.getDeparture().equals("") || route.getDestination().equals("") || route.getDeparture().equalsIgnoreCase(route.getDestination())
                || route.getMileage()<0 || route.getDurationH()<0) {
            logger.error("service[checkInputFields](wrongInputs) {}", route);
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }

    public void checkRouteIDAndDuplicates(Route route, List<Route> routeList) {
        if (routeList.size()>1) {
            logger.error("service[checkRouteIDAndDuplicates](dup) {}", route);
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        if (routeList.isEmpty() || routeList.get(0).getRouteID() != route.getRouteID()) {
            logger.error("service[checkRouteIDAndDuplicates](idNotFound) {}", route);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

}
