package com.fms.flights.models;


public class SearchFlightDTO {

    private String flightNumber;

    private String departure;

    private String arrival;

    private String departureDate;

    private String departureTime;

    private String arrivalDate;

    private String arrivalTime;

    public SearchFlightDTO() {
    }

    public SearchFlightDTO(String flightNumber, String departure, String arrival, String departureDate, String departureTime, String arrivalDate, String arrivalTime) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
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

    public boolean isContainsNonEmptyNotNullValues(){
        return !(flightNumber.isBlank() && departure.isBlank() && arrival.isBlank() && departureDate.isBlank() && departureTime.isBlank() &&
                arrivalDate.isBlank() && arrivalTime.isBlank());
    }

    @Override
    public String toString() {
        return "searchFlightDTO{" +
                "flightNumber=" + flightNumber +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", departureDate=" + departureDate +
                ", departureTime=" + departureTime +
                ", arrivalDate=" + arrivalDate +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
