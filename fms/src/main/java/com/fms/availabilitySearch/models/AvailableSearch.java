package com.fms.availabilitySearch.models;

import com.fms.fares.models.Fare;
import com.fms.flights.models.Flight;
import com.fms.routes.models.Route;

public class AvailableSearch {
    private Route route;
    private Flight flight;
    private Fare fare;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }
}
