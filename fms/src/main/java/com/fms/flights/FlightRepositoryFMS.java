package com.fms.flights;

import com.fms.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepositoryFMS extends JpaRepository<Flight,String> {

    List<Flight> findAllByFlightNumberAndDepartureDateAndIdNot(String flightNUmber, String departureDate, String id);
}
