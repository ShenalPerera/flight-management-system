package com.fms.flights.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "flight")
@Table
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "flight_id", updatable = false, nullable = false)
    private Long flightId;

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

    @CreationTimestamp
    @Column(
            name = "created_date_time",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp createdDateNTime;

    @UpdateTimestamp
    @Column(
            name = "modified_date_time",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private Timestamp modifiedDateNTime;

    @Version
    private long version;

    public Flight() {

    }

    public Flight(Long flightId, String flightNumber, String arrival, String departure, String arrivalDate, String departureDate, String arrivalTime, String departureTime) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.arrival = arrival;
        this.departure = departure;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long id) {
        this.flightId = id;
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

    public Timestamp getCreatedDateNTime() {
        return createdDateNTime;
    }

    public void setCreatedDateNTime(Timestamp createdDateNTime) {
        this.createdDateNTime = createdDateNTime;
    }

    public Timestamp getModifiedDateNTime() {
        return modifiedDateNTime;
    }

    public void setModifiedDateNTime(Timestamp modifiedDateNTime) {
        this.modifiedDateNTime = modifiedDateNTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightId, flight.flightId);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + flightId + '\'' +
                ", flight_number='" + flightNumber + '\'' +
                ", arrival='" + arrival + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival_date='" + arrivalDate + '\'' +
                ", departure_date='" + departureDate + '\'' +
                ", arrival_time='" + arrivalTime + '\'' +
                ", departure_time='" + departureTime + '\'' +
                '}';
    }

    public boolean isContainsEmptyFields() {
        return flightId == null  || flightNumber.isBlank() || arrival.isBlank() || departure.isBlank() || arrivalDate.isBlank() ||
                arrivalTime.isBlank() || departureDate.isBlank() || departureTime.isBlank();
    }

}
