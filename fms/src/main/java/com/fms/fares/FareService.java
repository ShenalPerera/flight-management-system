package com.fms.fares;

import com.fms.HttpStatusCodesFMS .HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.exceptions.*;
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
    private List<Fare> entries;
    private int length = 10;
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

        this.entries = new ArrayList<>();
        this.entries.add(new Fare(1, "colombo", "dubai", 50));
        this.entries.add(new Fare(2, "colombo", "sydney", 75));
        this.entries.add(new Fare(3, "dubai", "colombo", 50));
        this.entries.add(new Fare(4, "colombo", "new york", 150));
        this.entries.add(new Fare(5, "new york", "sydney", 225));

        this.entries.add(new Fare(6, "new york", "colombo", 150));
        this.entries.add(new Fare(7, "london", "colombo", 125));
        this.entries.add(new Fare(8, "dubai", "london", 80));
        this.entries.add(new Fare(9, "paris", "sydney", 185));
        this.entries.add(new Fare(10, "new york", "paris", 135));
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Fare> getSearchedEntries(String departure, String arrival) {
        if (departure.isEmpty() && arrival.isEmpty())
            return entries;
        else
            return this.entries.stream().filter(data ->
                    (data.getDeparture().equals(departure) && data.getArrival().equals(arrival))
                            || (departure.isEmpty() && data.getArrival().equals(arrival))
                            || (data.getDeparture().equals(departure) && arrival.isEmpty())
            ).collect(Collectors.toList());
    }

    private int isDuplicate(String departure, String arrival) {
        Fare duplicateEntry = this.entries.stream().filter(data ->
                (data.getDeparture().equals(departure) && data.getArrival().equals(arrival))
        ).findAny().orElse(null);
        return (duplicateEntry == null)? 0 : duplicateEntry.getId();
    }

    public Fare createEntry(Fare entry) {

        if ((entry.getDeparture() == null) || (entry.getArrival() == null) || (entry.getFare() == 0)) {
            logger.error("missing data from the query | departure [{}], arrival [{}], fare [{}]",
                    entry.getDeparture(), entry.getArrival(), entry.getFare());
            throw new MissingDataException();
        } else if (entry.getDeparture().equals(entry.getArrival())) {
            logger.error("departure [{}] and arrival [{}] are not distinct",
                    entry.getDeparture(), entry.getArrival());
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        } else if (entry.getFare() < 0) {
            logger.error("fare is negative [{}]", entry.getFare());
            throw new NegativeNumberException();
        } else if (isDuplicate(entry.getDeparture(), entry.getArrival()) != 0) {
            logger.error("a duplicate entry exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        } else if (entry.getDeparture().isEmpty() || entry.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure [{}], arrival [{}]",
                    entry.getDeparture(), entry.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }

        Fare newEntry = new Fare(++this.length, entry.getDeparture(), entry.getArrival(), entry.getFare());
        this.entries.add(newEntry);
        return newEntry;
    }

    public Fare editEntry(Fare entry) {

        if ((entry.getId() == 0) || (entry.getDeparture() == null) || (entry.getArrival() == null) || (entry.getFare() == 0)) {
            logger.error("missing data from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
                    entry.getId(), entry.getDeparture(), entry.getArrival(), entry.getFare());
            throw new MissingDataException();
        } else if (entry.getDeparture().equals(entry.getArrival())) {
            logger.error("departure [{}] and arrival [{}] are not distinct",
                    entry.getDeparture(), entry.getArrival());
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        } else if (entry.getFare() < 0) {
            logger.error("fare is negative [{}]", entry.getFare());
            throw new NegativeNumberException();
        } else if (entry.getDeparture().isEmpty() || entry.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure [{}], arrival [{}]",
                    entry.getDeparture(), entry.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }

        int duplicateId = isDuplicate(entry.getDeparture(), entry.getArrival());
        if  (duplicateId != entry.getId()) { // a duplicate entry may exist (the user given ID may not exist)

            if (duplicateId != 0) {
                logger.error("a duplicate entry exists for the given inputs");
                throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
            }
            else
                try {
                    Fare editedEntry = this.entries.stream().filter(data -> data.getId() == entry.getId())
                            .findAny().orElse(null);
                    editedEntry.setDeparture(entry.getDeparture());
                    editedEntry.setArrival(entry.getArrival());
                    editedEntry.setFare(entry.getFare());
                    return entry;
                } catch (NullPointerException e) { // the user given id doesn't exist
                    logger.error("an entry doesn't exist for the given id [{}]", entry.getId());
                    throw new IdDoesntExistException();
                }

        } else { // the user hasn't changed departure and location
            this.entries.stream().filter(data -> data.getId() == entry.getId())
                    .findAny().ifPresent(data -> {
                        data.setDeparture(entry.getDeparture());
                        data.setArrival(entry.getArrival());
                        data.setFare(entry.getFare());
                    });
            return entry;
        }
    }

    public int deleteEntry(int id) {
        if (!this.entries.removeIf(data -> data.getId() == id)) {
            logger.error("an entry doesn't exist for the given id [{}]", id);
            throw new IdDoesntExistException();
        } else {
            return id;
        }
    }
}
