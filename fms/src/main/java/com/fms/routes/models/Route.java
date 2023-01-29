package com.fms.routes.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int routeID;
    String departure;
    String destination;
    double mileage;
    double durationH;
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    String createdDateTime;
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
    String modifiedDateTime;

    public Route() {

    }
    public Route(int routeID, String departure, String destination,
                 double mileage, double durationH, String createdDateTime, String modifiedDateTime) {
        this.routeID = routeID;
        this.departure = departure;
        this.destination = destination;
        this.mileage = mileage;
        this.durationH = durationH;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
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

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
}
