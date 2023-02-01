package com.fms.availabilitySearch.models;


public class AvailableSearch {
   private String flightNumber;
   private String departure;
   private String arrival;
   private String departureStartDate;
   private String departureEndDate;
   private Double fare;

   public AvailableSearch(
           String flightNumber,
           String departure,
           String arrival,
           String departureStartDate,
           String departureEndDate,
           Double fare
   ) {
      this.flightNumber = flightNumber;
      this.departure = departure;
      this.arrival = arrival;
      this.departureStartDate = departureStartDate;
      this.departureEndDate = departureEndDate;
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

   public String getDepartureStartDate() {
      return departureStartDate;
   }

   public void setDepartureStartDate(String departureStartDate) {
      this.departureStartDate = departureStartDate;
   }

   public String getDepartureEndDate() {
      return departureEndDate;
   }

   public void setDepartureEndDate(String departureEndDate) {
      this.departureEndDate = departureEndDate;
   }

   public Double getFare() {
      return fare;
   }

   public void setFare(Double fare) {
      this.fare = fare;
   }

   @Override
   public String toString() {
      return "AvailableSearch{" +
              "flightNumber='" + flightNumber + '\'' +
              ", departure='" + departure + '\'' +
              ", arrival='" + arrival + '\'' +
              ", departureStartDate='" + departureStartDate + '\'' +
              ", departureEndDate='" + departureEndDate + '\'' +
              ", fare=" + fare +
              '}';
   }
}

