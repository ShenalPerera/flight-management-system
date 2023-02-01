package com.fms.fares.services;

import com.fms.fares.daos.FareDao;
import com.fms.fares.repositories.FareRepository;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.models.Fare;
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
    private final FareDao fareDao;

    @Autowired
    public FareService(FareRepository fareRepository, FareDao fareDao) {
        this.logger = LoggerFactory.getLogger(FareService.class);
        this.fareRepository = fareRepository;
        this.fareDao = fareDao;
    }

    // ************** database handlers **************

    public List<Fare> getSearchedFares(String departure, String arrival) {
        return fareDao.getSearchedFares(departure, arrival);
    }

    public Fare createFare(Fare fare) {
        validateInputs(fare);
        checkMissingData(fare);
        logger.info("created DTO send to DB | " + fare);
        try {
            return fareRepository.save(fare);
        } catch (DataIntegrityViolationException e) {
            logger.error("a duplicate fare exists for the given inputs | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    public Fare editFare(Fare editedFare) {
        validateInputs(editedFare);
        checkMissingDataWithId(editedFare);
        checkDuplicateFaresAndExistence(editedFare);
        logger.info("updated DTO send to DB | " + editedFare);
        try {
            return fareRepository.save(editedFare);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            logger.error("version mismatch | attempt to update with an older version [{}]", editedFare.getVersion());
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }
    }

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

    private List<Fare> getFaresForValidation(String departure, String arrival, int id) {
        return fareRepository.findByDepartureAndArrivalOrId(departure, arrival, id);
    }

    // ***************** validations *****************

    private void validateInputs(Fare userFare) {
        checkSameLocation(userFare);
        checkNegativeValues(userFare);
        checkEmptyStrings(userFare);
    }
    private void checkMissingData(Fare fare) {
        if ((fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("some data is missing from the query | departure [{}], arrival [{}], fare [{}]",
                    fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }
    private void checkMissingDataWithId(Fare fare) {
        if ((fare.getId() == 0)
                || (fare.getDeparture() == null)
                || (fare.getArrival() == null)
                || (fare.getFare() == 0)) {
            logger.error("some data is missing from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
                    fare.getId(), fare.getDeparture(), fare.getArrival(), fare.getFare());
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
    private void checkDuplicateFaresAndExistence(Fare fare) {
        List<Fare> fareList = getFaresForValidation(fare.getDeparture(), fare.getArrival(), fare.getId());
        if (fareList.size() > 1) {
            logger.error("a duplicate fare exists for the given inputs | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        if (fareList.isEmpty() || (fareList.get(0).getId() != fare.getId())) {
            logger.error("a fare doesn't exist for the given id [{}]", fare.getId());
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }
    private void checkEmptyStrings(Fare fare) {
        if (fare.getDeparture().isEmpty() || fare.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure '{}', arrival '{}'",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }
}
