package com.fms.flights.repositories;

import com.fms.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepositoryFMS extends JpaRepository<Flight, String> {
    List<Flight> findAllByFlightNumberAndDepartureDateOrFlightId(String flightNUmber, String departureDate, String id);

    List<Flight> findAllByFlightNumberAndDepartureDate(String flightNUmber, String departureDate);
}
