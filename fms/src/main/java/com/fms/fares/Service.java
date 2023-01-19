package com.fms.fares;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    private final List<String> locations;
    private List<Model> entries;
    private int length = 10;

    public Service() {
        this.locations = new ArrayList<String>();
        this.locations.add("colombo");
        this.locations.add("dubai");
        this.locations.add("london");
        this.locations.add("new york");
        this.locations.add("paris");
        this.locations.add("sydney");

        this.entries = new ArrayList<Model>();
        this.entries.add(new Model(1, "colombo", "dubai", 50));
        this.entries.add(new Model(2, "colombo", "sydney", 75));
        this.entries.add(new Model(3, "dubai", "colombo", 50)); //
        this.entries.add(new Model(4, "colombo", "new york", 150));
        this.entries.add(new Model(5, "new york", "sydney", 225));

        this.entries.add(new Model(6, "new york", "colombo", 150));
        this.entries.add(new Model(7, "london", "colombo", 125));
        this.entries.add(new Model(8, "dubai", "london", 80));
        this.entries.add(new Model(9, "paris", "sydney", 185));
        this.entries.add(new Model(10, "new york", "paris", 135));
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Model> getSearchedEntries(String departure, String arrival) {
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
        Model duplicateEntry = this.entries.stream().filter(data ->
                (data.getDeparture().equals(departure) && data.getArrival().equals(arrival))
        ).findAny().orElse(null);
        return (duplicateEntry == null)? 0 : duplicateEntry.getId();
    }

    public Model createEntry(Model entry) {
        if (isDuplicate(entry.getDeparture(), entry.getArrival()) == 0) {
            Model newEntry = new Model(++this.length, entry.getDeparture(), entry.getArrival(), entry.getFare());
            this.entries.add(newEntry);
            return newEntry;
        } else {
            return null;
        }
    }

    public Model editEntry(Model entry) {
        int duplicateId = isDuplicate(entry.getDeparture(), entry.getArrival());
        if ((duplicateId != 0) && (duplicateId != entry.getId())) {
            return null;
        } else {
            Model editedEntry = this.entries.stream().filter(data -> data.getId() == entry.getId())
                    .findAny().orElse(null);
            editedEntry.setDeparture(entry.getDeparture());
            editedEntry.setArrival(entry.getArrival());
            editedEntry.setFare(entry.getFare());
            return entry;
        }
    }

    public int deleteEntry(int id) {
        this.entries.removeIf(data -> data.getId() == id);
        return id;
    }
}
