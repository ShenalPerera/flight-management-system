package com.fms.flights.repositories;

import com.fms.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepositoryFMS extends JpaRepository<Flight,String> {
    List<Flight> findAllByFlightNumberAndDepartureDateOrId(String flightNUmber, String departureDate, String id);

    List<Flight> findAllByFlightNumberAndDepartureDate(String flightNUmber, String departureDate);
}
