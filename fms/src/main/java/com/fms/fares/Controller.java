package com.fms.fares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/api/fares/entries")
    public List<Model> getEntries() {
        return service.getEntries();
    }

    @GetMapping("/api/fares/search")
    public List<Model> getSearchedEntries(
            @RequestParam(value = "departure", defaultValue = "") String departure,
            @RequestParam(value = "arrival", defaultValue = "") String arrival) {
        return service.getSearchedEntries(departure, arrival);
    }
}
