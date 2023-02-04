package com.fms.routes.services;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.routes.daos.RouteDao;
import com.fms.routes.models.Route;
import com.fms.routes.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

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

    public List<Route> sendAllRoutes() {
        logger.info("service[sendAllRoutes]");
        return routeDao.getAllRoutes();
    }

    public Route getOneRoute(int routeID) {
        logger.info("service[getOneRoute]");
        return routeRepository.findByRouteID(routeID);
    }

    public Route createRoute(Route route) {
        logger.info("service[createRoute] {}", route);
        checkInputFields(route);
        Route routeToBeActive = routeRepository.findByDepartureAndDestination(route.getDeparture(), route.getDestination());

        if (routeToBeActive != null) {
            if (routeToBeActive.getStatus() == 1) {
                logger.error("service[createRoute](exists) {}", route);
                throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
            }else {
                routeToBeActive.setStatus(1);
                routeToBeActive.setMileage(route.getMileage());
                routeToBeActive.setDurationH(route.getDurationH());
                try {
                    Route createdRoute = routeRepository.save(routeToBeActive);
                    logger.info("service[createRoute](new) {}", route);
                    return createdRoute;
                }catch (DataIntegrityViolationException e) {
                    logger.error("service[createRoute](exists) {}", route);
                    throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
                }
            }
        }else {
            return createIfRouteIsNew(route);
        }

    }


    public Route editRoute(Route route) {
        checkInputFields(route);

        Route routeToBeUpdated = routeRepository.findByRouteID(route.getRouteID());
        if (routeToBeUpdated == null) {
            logger.error("service[checkRouteIDAndDuplicates](idNotFound) {}", route);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }

        try {
            route.setCreatedDateTime(routeToBeUpdated.getCreatedDateTime());
            Route updatedRoute = routeRepository.save(route);
            logger.info("service[edit] {}", route);
            return updatedRoute;
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.error("service[edit](dirtyUpdate) {}", route);
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }

    }

    public int deleteRoute(int routeID){
            Route routeToBeDeleted = routeRepository.findByRouteID(routeID);
            if (routeToBeDeleted == null) {
                logger.error("service[deleteRoute] id->{}", routeID);
                throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
            }
            if (checkErrorsBeforeDeleteRoute(routeToBeDeleted)) {
                logger.error("service[deleteRoute] id->{}", routeID);
                throw new FMSException(HttpStatusCodesFMS.CANNOT_BE_EXECUTED);
            }
            routeToBeDeleted.setStatus(0);
            routeRepository.save(routeToBeDeleted);
            return routeID;

    }



    public List<Route> searchRoutes(String departure, String destination) {
        return routeDao.searchRoutes(departure, destination);

    }

    public Route createIfRouteIsNew(Route route) {
        logger.info("service[createIfRouteIsNew](new) {}", route);
        try {
            route.setStatus(1);
            Route createdRoute = routeRepository.save(route);
            logger.info("service[createRoute](new) {}", route);
            return createdRoute;
        }catch (DataIntegrityViolationException e) {
            logger.error("service[createRoute](duplicate) {}", route);
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    public boolean checkErrorsBeforeDeleteRoute(Route routeToBeDeleted){
        return (
                getNumOfCombinationsInFares(routeToBeDeleted.getDeparture(), routeToBeDeleted.getDestination()) > 0 ||
                getNumOfCombinationsInFlights(routeToBeDeleted.getDeparture(), routeToBeDeleted.getDestination()) > 0
                );

    }

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

}
