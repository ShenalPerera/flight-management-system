package com.fms.fares.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "departure", "arrival" }) })
public class Fare {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String departure;
    private String arrival;
    private double fare;
    private Timestamp createdTimestamp;
    private Timestamp modifiedTimestamp;
    @Version
    private long version;

    public Fare() {}

    public Fare(
            int id,
            String departure,
            String arrival,
            double fare
    ) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
    }

    public Fare(
            int id,
            String departure,
            String arrival,
            double fare,
            Timestamp createdTimestamp,
            Timestamp modifiedTimestamp,
            long version
    ) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.fare = fare;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
        this.version = version;
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

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "id=" + id +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", fare=" + fare +
                ", createdTimestamp=" + createdTimestamp +
                ", modifiedTimestamp=" + modifiedTimestamp +
                ", version=" + version +
                '}';
    }
}
