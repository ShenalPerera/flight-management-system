package com.fms.fares;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {
    private final List<String> locations;
    private List<Model> entries;

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

    public List<Model> getEntries() {
        return entries;
    }

    public List<Model> getSearchedEntries(String departure, String arrival) {
        return this.entries.stream().filter(data ->
                (data.getDeparture().equals(departure) && data.getArrival().equals(arrival))
                        || (departure.isEmpty() && data.getArrival().equals(arrival))
                        || (data.getDeparture().equals(departure) && arrival.isEmpty())
        ).collect(Collectors.toList());
    }
}
