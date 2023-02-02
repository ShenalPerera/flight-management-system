package com.fms.availabilitySearch.models;


public class AvailableSearch {
   private String flightNumber;
   private String departure;
   private String arrival;
   private String departureStartDate;
   private String departureEndDate;

   public AvailableSearch(
           String flightNumber,
           String departure,
           String arrival,
           String departureStartDate,
           String departureEndDate
   ) {
      this.flightNumber = flightNumber;
      this.departure = departure;
      this.arrival = arrival;
      this.departureStartDate = departureStartDate;
      this.departureEndDate = departureEndDate;
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

