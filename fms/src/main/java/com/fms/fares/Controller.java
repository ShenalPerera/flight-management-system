package com.fms.fares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<String>> getLocations() {
        return new ResponseEntity<List<String>>(service.getLocations(), HttpStatus.OK);
    }

    @GetMapping("/api/fares/search")
    public ResponseEntity<List<Model>> getSearchedEntries(
            @RequestParam(value = "departure", defaultValue = "") String departure,
            @RequestParam(value = "arrival", defaultValue = "") String arrival) {
        return new ResponseEntity<List<Model>>(service.getSearchedEntries(departure, arrival), HttpStatus.OK);
    }

    @PostMapping("/api/fares/entry")
    public ResponseEntity<Model> createEntry(@RequestBody Model entry) {
        return new ResponseEntity<Model>(service.createEntry(entry), HttpStatus.CREATED);
    }

    @PutMapping("/api/fares/entry")
    public ResponseEntity<Model> editEntry(@RequestBody Model entry) {
        return new ResponseEntity<Model>(service.editEntry(entry), HttpStatus.OK);
    }

    @DeleteMapping("/api/fares/entry")
    public ResponseEntity<Integer> deleteEntry(@RequestParam(value = "id") int id) {
        return new ResponseEntity<Integer>(service.deleteEntry(id), HttpStatus.NO_CONTENT);
    }
}
