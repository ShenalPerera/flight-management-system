package com.fms.routes_screen.models;

public class Route {
    int routeID;
    String departure;
    String destination;
    double mileage;
    double durationH;

    public Route(int routeID, String departure, String destination, double mileage, double durationH) {
        this.routeID = routeID;
        this.departure = departure;
        this.destination = destination;
        this.mileage = mileage;
        this.durationH = durationH;
    }
}
