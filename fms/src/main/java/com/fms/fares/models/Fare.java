package com.fms.fares.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "departure", "arrival" }) })
public class Fare {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int fareId;
    private String departure;
    private String arrival;
    private double fare;
    @Column(updatable = false)
    private Timestamp createdDateTime;
    @Column(updatable = false)
    private Timestamp modifiedDateTime;
    @Version
    private long version;

    public Fare() {}

    public Fare(
            int fareId,
            String departure,
            String arrival,
            double fare
    ) {
        this.fareId = fareId;
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
    }

    public Fare(
            int fareId,
            String departure,
            String arrival,
            double fare,
            Timestamp createdDateTime,
            Timestamp modifiedDateTime,
            long version
    ) {
        this.fareId = fareId;
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.version = version;
    }

    public int getFareId() {
        return fareId;
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

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "id=" + fareId +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", fare=" + fare +
                ", createdDateTime=" + createdDateTime +
                ", modifiedDateTime=" + modifiedDateTime +
                ", version=" + version +
                '}';
    }
}
