package com.fms.flights.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "Flight")
@Table
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "flight_id", updatable = false, nullable = false)
    private String id;

    @Column(
            name = "flight_number",
            nullable = false,
            columnDefinition = "CHAR(4)"
    )
    private String flightNumber;

    @Column(
            name = "departure",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String departure;

    @Column(
            name = "arrival",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String arrival;

    @Column(
            name = "departure_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private String departureDate;

    @Column(
            name = "arrival_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private String arrivalDate;


    @Column(
            name = "departure_time",
            nullable = false,
            columnDefinition = "TIME"
    )
    private String departureTime;


    @Column(
            name = "arrival_time",
            nullable = false,
            columnDefinition = "TIME"
    )
    private String arrivalTime;


    public Flight(String id, String flightNumber, String arrival, String departure, String arrivalDate, String departureDate, String arrivalTime, String departureTime) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.arrival = arrival;
        this.departure = departure;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public Flight() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flight_number) {
        this.flightNumber = flight_number;
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

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrival_date) {
        this.arrivalDate = arrival_date;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departure_date) {
        this.departureDate = departure_date;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrival_time) {
        this.arrivalTime = arrival_time;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departure_time) {
        this.departureTime = departure_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", flight_number='" + flightNumber + '\'' +
                ", arrival='" + arrival + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival_date='" + arrivalDate + '\'' +
                ", departure_date='" + departureDate + '\'' +
                ", arrival_time='" + arrivalTime + '\'' +
                ", departure_time='" + departureTime + '\'' +
                '}';
    }

    public boolean isContainsEmptyFields(){
        return id.isBlank() || flightNumber.isBlank() || arrival.isBlank() || departure.isBlank() || arrivalDate.isBlank()||
                arrivalTime.isBlank() || departureDate.isBlank() || departureTime.isBlank();
    }

}
