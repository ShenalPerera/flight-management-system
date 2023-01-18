package com.fms.fares;

public class Model {
    private int id;
    private String departure;
    private String arrival;
    private double fare;

    public Model(int id, String departure, String arrival, double fare) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public double getFare() {
        return fare;
    }
}
