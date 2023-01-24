package com.fms.fares;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.models.Fare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FareService {
    private final List<String> locations;
    private List<Fare> fares;
    private int length;
    private final Logger logger;

    public FareService() {
        this.logger = LoggerFactory.getLogger(FareService.class);

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

        this.length = fares.size();
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

    private int isDuplicate(String departure, String arrival) {
        Fare duplicateEntry = this.fares.stream().filter(data ->
                (data.getDeparture().equalsIgnoreCase(departure) && data.getArrival().equalsIgnoreCase(arrival))
        ).findAny().orElse(null);
        return (duplicateEntry == null)? 0 : duplicateEntry.getId();
    }

    public Fare createFare(Fare fare) {

        checkMissingData(fare);
        checkSameLocation(fare);
        checkNegativeValues(fare);
        checkDuplicateFares(fare);
        checkEmptyStrings(fare);

        Fare newEntry = new Fare(++this.length, fare.getDeparture(), fare.getArrival(), fare.getFare());
        this.fares.add(newEntry);
        return newEntry;
    }

    public Fare editFare(Fare fare) {

        checkMissingDataWithId(fare);
        checkSameLocation(fare);
        checkNegativeValues(fare);
        checkEmptyStrings(fare);

        int duplicateId = isDuplicate(fare.getDeparture(), fare.getArrival());
        if  (duplicateId != fare.getId()) { // a duplicate fare may exist (the user given ID may not exist)

            if (duplicateId != 0) {
                logger.error("a duplicate fare exists for the given inputs");
                throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
            }
            else
                try {
                    Fare editedEntry = this.fares.stream().filter(data -> data.getId() == fare.getId())
                            .findAny().orElse(null);
                    editedEntry.setDeparture(fare.getDeparture());
                    editedEntry.setArrival(fare.getArrival());
                    editedEntry.setFare(fare.getFare());
                    return fare;
                } catch (NullPointerException e) { // the user given id doesn't exist
                    logger.error("an fare doesn't exist for the given id [{}]", fare.getId());
                    throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
                }

        } else { // the user hasn't changed departure and location
            this.fares.stream().filter(data -> data.getId() == fare.getId())
                    .findAny().ifPresent(data -> {
                        data.setDeparture(fare.getDeparture());
                        data.setArrival(fare.getArrival());
                        data.setFare(fare.getFare());
                    });
            return fare;
        }
    }

    public int deleteFare(int id) {
        if (!this.fares.removeIf(data -> data.getId() == id)) {
            logger.error("an entry doesn't exist for the given id [{}]", id);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        } else {
            return id;
        }
    }

    // validations

    private void checkMissingData(Fare fare) {
        if ((fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("missing data from the query | departure [{}], arrival [{}], fare [{}]",
                    fare.getDeparture(), fare.getArrival(), fare.getFare());
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
    private void checkMissingDataWithId(Fare fare) {
        if ((fare.getId() == 0) || (fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("missing data from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
                    fare.getId(), fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }
    private void checkNegativeValues(Fare fare) {
        if (fare.getFare() < 0) {
            logger.error("fare is negative [{}]", fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.NEGATIVE_NUMBER);
        }
    }
    private void checkDuplicateFares(Fare fare) {
        if (isDuplicate(fare.getDeparture(), fare.getArrival()) != 0) {
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
}
