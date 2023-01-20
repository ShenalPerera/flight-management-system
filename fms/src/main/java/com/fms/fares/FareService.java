package com.fms.fares;

import com.fms.fares.exceptions.DuplicateEntryException;
import com.fms.fares.exceptions.IdDoesntExistException;
import com.fms.fares.exceptions.SameLocationException;
import com.fms.fares.models.Fare;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FareService {
    private final List<String> locations;
    private List<Fare> entries;
    private int length = 10;

    public FareService() {
        this.locations = new ArrayList<String>();
        this.locations.add("colombo");
        this.locations.add("dubai");
        this.locations.add("london");
        this.locations.add("new york");
        this.locations.add("paris");
        this.locations.add("sydney");

        this.entries = new ArrayList<Fare>();
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
        if (entry.getDeparture().equals(entry.getArrival()))
            throw new SameLocationException();
        if (isDuplicate(entry.getDeparture(), entry.getArrival()) == 0) {
            Fare newEntry = new Fare(++this.length, entry.getDeparture(), entry.getArrival(), entry.getFare());
            this.entries.add(newEntry);
            return newEntry;
        } else {
            throw new DuplicateEntryException();
        }
    }

    public Fare editEntry(Fare entry) {

        if (entry.getDeparture().equals(entry.getArrival()))
            throw new SameLocationException();

        int duplicateId = isDuplicate(entry.getDeparture(), entry.getArrival());
        if  (duplicateId != entry.getId()) { // a duplicate entry may exist (the user given ID may not exist)

            if (duplicateId != 0)
                throw new DuplicateEntryException();
            else
                try {
                    Fare editedEntry = this.entries.stream().filter(data -> data.getId() == entry.getId())
                            .findAny().orElse(null);
                    editedEntry.setDeparture(entry.getDeparture());
                    editedEntry.setArrival(entry.getArrival());
                    editedEntry.setFare(entry.getFare());
                    return entry;
                } catch (NullPointerException e) { // the user given id doesn't exist
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
        if (!this.entries.removeIf(data -> data.getId() == id))
            throw new IdDoesntExistException();
        else
            return id;
    }
}
