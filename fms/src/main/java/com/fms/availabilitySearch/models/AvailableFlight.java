package com.fms.availabilitySearch.models;

public class AvailableFlight {
    private String flightNumber;
    private String departure;
    private String arrival;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private Double fare;

    public AvailableFlight(
            String flightNumber,
            String departure,
            String arrival,
            String departureDate,
            String departureTime,
            String arrivalDate,
            String arrivalTime,
            Double fare
    ) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "AvailableFlight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", fare=" + fare +
                '}';
    }
}
