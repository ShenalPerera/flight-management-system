package com.fms.fares.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fare {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String departure;
    private String arrival;
    private double fare;

    public Fare() {}

    public Fare(String departure, String arrival, double fare) {
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
    }

    public Fare(int id, String departure, String arrival, double fare) {
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

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
