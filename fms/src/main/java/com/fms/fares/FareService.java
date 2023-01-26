package com.fms.fares;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.models.Fare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FareService {
    private final List<String> locations;
    private List<Fare> fares;
    private final Logger logger;
    private final FareRepository fareRepository;

    @Autowired
    public FareService(FareRepository fareRepository) {
        this.logger = LoggerFactory.getLogger(FareService.class);
        this.fareRepository = fareRepository;

        this.locations = new ArrayList<>();
        this.locations.add("colombo");
        this.locations.add("dubai");
        this.locations.add("london");
        this.locations.add("new york");
        this.locations.add("paris");
        this.locations.add("sydney");

        this.fares = new ArrayList<>();
        this.fares.add(new Fare(1, "colombo", "dubai", 50));
        this.fares.add(new Fare(2, "colombo", "sydney", 75));
        this.fares.add(new Fare(3, "dubai", "colombo", 50));
        this.fares.add(new Fare(4, "colombo", "new york", 150));
        this.fares.add(new Fare(5, "new york", "sydney", 225));

        this.fares.add(new Fare(6, "new york", "colombo", 150));
        this.fares.add(new Fare(7, "london", "colombo", 125));
        this.fares.add(new Fare(8, "dubai", "london", 80));
        this.fares.add(new Fare(9, "paris", "sydney", 185));
        this.fares.add(new Fare(10, "new york", "paris", 135));
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Fare> getSearchedFares(String departure, String arrival) {
        if (departure.isEmpty() && arrival.isEmpty())
            return fares;
        else
            return this.fares.stream().filter(data ->
                    (departure.equalsIgnoreCase(data.getDeparture()) && arrival.equalsIgnoreCase(data.getArrival()))
                            || (departure.isEmpty() && arrival.equalsIgnoreCase(data.getArrival()))
                            || (departure.equalsIgnoreCase(data.getDeparture()) && arrival.isEmpty())
            ).collect(Collectors.toList());
    }

    public Fare createFare(Fare fare) {
        validateInputs(fare);
        checkMissingData(fare);
        checkDuplicateFares(fare);
        return this.fareRepository.save(fare);
    }

    public Fare editFare(Fare userFare) {
        validateInputs(userFare);
        checkMissingDataWithId(userFare);
        checkDuplicateFaresWithId(userFare);
        checkObjectExistence(userFare);
        this.fareRepository.save(userFare);
        return userFare;
    }

    public int deleteFare(int id) {
        try {
            this.fareRepository.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            logger.error("an entry doesn't exist for the given id [{}]", id);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

    // utility functions

    private boolean isDuplicate(String departure, String arrival) {
        return this.fareRepository.findFirstByDepartureAndArrival(departure, arrival) != null;
    }
    private boolean isDuplicateWithId(String departure, String arrival, int id) {
        return this.fareRepository.findFirstByDepartureAndArrivalAndIdNot(departure, arrival, id) != null;
    }

    // validations

    private void validateInputs(Fare userFare) {
        checkSameLocation(userFare);
        checkNegativeValues(userFare);
        checkEmptyStrings(userFare);
    }
    private void checkMissingData(Fare fare) {
        if ((fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("missing data from the query | departure [{}], arrival [{}], fare [{}]",
                    fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }
    private void checkMissingDataWithId(Fare fare) {
        if ((fare.getId() == 0)
                || (fare.getDeparture() == null)
                || (fare.getArrival() == null)
                || (fare.getFare() == 0)) {
            logger.error("missing data from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
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
    private void checkDuplicateFares(Fare fare) {
        if (isDuplicate(fare.getDeparture(), fare.getArrival())) {
            logger.error("a duplicate fare exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }
    private void checkDuplicateFaresWithId(Fare fare) {
        if (isDuplicateWithId(fare.getDeparture(), fare.getArrival(), fare.getId())) {
            logger.error("a duplicate fare exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }
    private void checkEmptyStrings(Fare fare) {
        if (fare.getDeparture().isEmpty() || fare.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }
    private void checkObjectExistence(Fare userFare) {
        if (!this.fareRepository.existsById(userFare.getId())) {
            logger.error("a fare doesn't exist for the given id [{}]", userFare.getId());
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }
}
