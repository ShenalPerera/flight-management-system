package com.fms.routes.models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"departure", "destination"})})
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int routeID;
    private String departure;
    private String destination;
    private double mileage;
    private double durationH;
    private Timestamp createdDateTime;
    private Timestamp modifiedDateTime;
    @Version
    private long version;

    public Route() {
    }

    public Route(int routeID, String departure, String destination, double mileage, double durationH, Timestamp createdDateTime, Timestamp modifiedDateTime, long version) {
        this.routeID = routeID;
        this.departure = departure;
        this.destination = destination;
        this.mileage = mileage;
        this.durationH = durationH;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.version = version;
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

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }
    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Timestamp getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Timestamp modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
