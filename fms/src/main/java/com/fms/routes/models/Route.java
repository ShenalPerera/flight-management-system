package com.fms.routes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

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
    LocalDateTime createdDateTime;
//    @UpdateTimestamp
    LocalDateTime modifiedDateTime;

    public Route() {

    }
    public Route(int routeID, String departure, String destination,
                 double mileage, double durationH, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
}
