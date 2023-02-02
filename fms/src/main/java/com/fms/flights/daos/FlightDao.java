package com.fms.flights.daos;

import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;

import java.util.List;


public interface FlightDao {
    List<Flight> findAllFlightsByGivenOptions(SearchFlightDTO searchFlightDTO);
}
