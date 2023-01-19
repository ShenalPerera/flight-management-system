package com.fms.routes_screen.models;

public class Route {
    int routeID;
    String departure;
    String destination;
    double mileage;
    double durationH;

    public Route() {

    }
    public Route(int routeID, String departure, String destination, double mileage, double durationH) {
        this.routeID = routeID;
        this.departure = departure;
        this.destination = destination;
        this.mileage = mileage;
        this.durationH = durationH;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getDurationH() {
        return durationH;
    }

    public void setDurationH(double durationH) {
        this.durationH = durationH;
    }
}
