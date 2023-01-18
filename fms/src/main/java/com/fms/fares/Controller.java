package com.fms.fares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class Controller {
    private final Service service;

    @Autowired
    Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/api/fares/locations")
    public List<String> getLocations() {
        return service.getLocations();
    }

    @GetMapping("/api/fares/search")
    public List<Model> getSearchedEntries(
            @RequestParam(value = "departure", defaultValue = "") String departure,
            @RequestParam(value = "arrival", defaultValue = "") String arrival) {
        return service.getSearchedEntries(departure, arrival);
    }

    @PostMapping("/api/fares/entry")
    public Model createEntry(@RequestBody Model entry) {
        return service.createEntry(entry);
    }
}
