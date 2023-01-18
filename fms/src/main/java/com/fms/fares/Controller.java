package com.fms.fares;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/api/fares/hello")
    public String sayHello() {
        return "hello";
    }
    String[] locations = { "colombo", "dubai" };
    @GetMapping("/api/fares/locations")
    public String[] getLocations() {
        return locations;
    }
}
