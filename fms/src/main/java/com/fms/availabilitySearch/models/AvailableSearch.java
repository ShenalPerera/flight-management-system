package com.fms.availabilitySearch.models;


public class AvailableSearch {
   private String flightNumber;
   private String departure;
   private String destination;
   private String departureDate;
   private String departureTime;
   private String arrivalDate;
   private String arrivalTime;
   private double fare;

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

   public String getDestination() {
      return destination;
   }

   public void setDestination(String destination) {
      this.destination = destination;
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

   public double getFare() {
      return fare;
   }

   public void setFare(double fare) {
      this.fare = fare;
   }
}

