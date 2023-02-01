package com.fms.flights.repositories;

import com.fms.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findAllByFlightNumberAndDepartureDateOrFlightId(String flightNUmber, String departureDate, Long id);

    List<Flight> findAllByFlightNumberAndDepartureDate(String flightNUmber, String departureDate);

    int countAllByFlightNumberAndDepartureDate(String flightNumber, String departureDate);

    @Query("SELECT CASE WHEN COUNT(f) = 1 AND f.flightId = : flightId THEN TRUE ELSE FALSE END FROM flight f WHERE " +
            "f.flightNumber = :flightNumber AND f.departureDate = :departureDate or f.flightId = :flightId")
    boolean isFlightExitsAndNoDuplicatesFound(@Param("flightNumber") String flightNumber,@Param("departureDate") String departureDate,@Param("flightId") Long flightId);
}
