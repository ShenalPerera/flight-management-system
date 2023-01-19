package com.fms.flights;

public class Flight {
    public String id;
    public String flight_number;
    public String arrival;
    public String departure;
    public String arrival_date;
    public String departure_date;

    public String arrival_time;

    public String departure_time;

    public Flight(String id, String flight_number, String arrival, String departure, String arrival_date, String departure_date, String arrival_time, String departure_time) {
        this.id = id;
        this.flight_number = flight_number;
        this.arrival = arrival;
        this.departure = departure;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }
}
