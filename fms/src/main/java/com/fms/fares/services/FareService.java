package com.fms.fares.services;

import com.fms.fares.daos.FareDao;
import com.fms.fares.repositories.FareRepository;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.models.Fare;
import com.fms.routes.repositories.RouteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareService {

    private final Logger logger;
    private final FareRepository fareRepository;
    private final RouteRepository routeRepository;
    private final FareDao fareDao;

    @Autowired
    public FareService(FareRepository fareRepository, RouteRepository routeRepository, FareDao fareDao) {
        this.logger = LoggerFactory.getLogger(FareService.class);
        this.fareRepository = fareRepository;
        this.routeRepository = routeRepository;
        this.fareDao = fareDao;
    }

    // ************** database handlers **************

    public List<Fare> getSearchedFares(String departure, String arrival) {
        return fareDao.getSearchedFares(departure, arrival);
    }

    @Transactional
    public Fare createFare(Fare fare) {
        validateCreateFare(fare);
        logger.info("created DTO send to DB | " + fare);
        try {
            return fareRepository.save(fare);
        } catch (DataIntegrityViolationException e) {
            logger.error("a duplicate fare exists for the given inputs | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    @Transactional
    public Fare editFare(Fare editedFare) {
        validateEditFare(editedFare);
        logger.info("updated DTO send to DB | " + editedFare);
        try {
            return fareRepository.save(editedFare);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            logger.error("version mismatch | attempt to update with an older version [{}]", editedFare.getVersion());
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }
    }

    @Transactional
    public int deleteFare(int id) {
        try {
            fareRepository.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            logger.error("no entry exists for the given id [{}]", id);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

    // ************** utility functions **************

    private void validateCreateFare(Fare fare) {
        checkSameLocation(fare);
        checkNegativeValues(fare);
        checkEmptyStrings(fare);
        checkMissingData(fare);
        checkRouteExistence(fare);
    }

    private void validateEditFare(Fare fare) {
        checkNegativeValues(fare);
        checkMissingDataWithId(fare);
    }

    // ***************** validations *****************

    private void checkMissingData(Fare fare) {
        if ((fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("some data is missing from the query | departure [{}], arrival [{}], fare [{}]",
                    fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }

    private void checkMissingDataWithId(Fare fare) {
        if ((fare.getFareId() == 0) || (fare.getFare() == 0)) {
            logger.error("some data is missing from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
                    fare.getFareId(), fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }

    private void checkSameLocation(Fare fare) {
        if (fare.getDeparture().equalsIgnoreCase(fare.getArrival())) {
            logger.error("departure [{}] and arrival [{}] are not distinct",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
    }

    private void checkNegativeValues(Fare fare) {
        if (fare.getFare() < 0) {
            logger.error("fare is negative [{}]", fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.NEGATIVE_NUMBER);
        }
    }

    private void checkEmptyStrings(Fare fare) {
        if (fare.getDeparture().isEmpty() || fare.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure '{}', arrival '{}'",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }

    @Transactional
    private void checkRouteExistence(Fare fare) {
        if (!routeRepository.existsRouteByDepartureAndDestination(fare.getDeparture(), fare.getArrival())) {
            logger.error("a route doesn't exist for the given departure [{}] and arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.ROUTE_DOESNT_EXIST);
        }
    }
}
