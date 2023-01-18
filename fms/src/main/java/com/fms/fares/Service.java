package com.fms.fares;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    private final List<String> locations;
    private List<Model> entries;
    private int length = 3;

    public Service() {
        this.locations = new ArrayList<String>();
        this.locations.add("colombo");
        this.locations.add("sydney");
        this.entries = new ArrayList<Model>();
        this.entries.add(new Model(1, "colombo", "dubai", 50));
        this.entries.add(new Model(2, "colombo", "sydney", 75));
        this.entries.add(new Model(3, "dubai", "colombo", 50));
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
}
