package com.fms.flights;

import com.fms.flights.models.Flight;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class FlightRepository {

    private final List<Flight> flights = new ArrayList<>();

    FlightRepository() {
        flights.add(new Flight("1", "1", "colombo", "India", "2023-01-04", "2023-01-05", "13:04", "11:00"));
    }


//    public List<Flight> getAll() {
//        return flights;
//    }
//
//    public Flight addEntry(Flight flight) {
//        flights.add(flight);
//        return flight;
//    }
//
//    public Flight deleteEntryById(String id) {
//        Flight deletedFlight = flights.stream().filter(flight -> Objects.equals(flight.getId(), id)).findFirst().orElse(null);
//        boolean isDeleted = flights.remove(deletedFlight);
//        if (isDeleted) {
//            return deletedFlight;
//        }
//        return null;
//    }
//
//    public Flight editFlight(Flight editedFlight) {
//        try {
//            return flights.set(flights.indexOf(editedFlight), editedFlight);
//        } catch (IndexOutOfBoundsException e) {
//            return null;
//        }
//
//    }
//
//    public List<Flight> getFlightsByFlightNumberNDepartureDate(Flight flight) {
//        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() + "T" + flight.getDepartureTime());
//
//        return flights.stream().filter(flightEntry ->
//                        Objects.equals(flightEntry.getFlightNumber(), flight.getFlightNumber()) &&
//                                departureDateNTime.isEqual(LocalDateTime.parse(flightEntry.getDepartureDate() + "T" + flightEntry.getDepartureTime())) &&
//                                !Objects.equals(flightEntry.getId(), flight.getId())
//
//                ).
//                collect(Collectors.toList());
//    }

    public List<Flight> getFLightsByParameters(Flight flight) {
        List<Flight> filteredFlights = new ArrayList<>();
        boolean isMatchFlightNUmber;
        boolean isMatchDeparture;
        boolean isMatchArrival;
        boolean isMatchDepartureDate;
        boolean isMatchArrivalDate;
        boolean isMatchDepartureTime;
        boolean isMatchArrivalTime;

        for (Flight flightEntry : flights) {

            isMatchFlightNUmber = true;
            isMatchDeparture = true;
            isMatchArrival = true;
            isMatchDepartureDate = true;
            isMatchArrivalDate = true;
            isMatchDepartureTime = true;
            isMatchArrivalTime = true;

            if (!flight.getFlightNumber().isBlank()) {
                isMatchFlightNUmber = flight.getFlightNumber().equals(flightEntry.getFlightNumber());

            }
            if (!flight.getDeparture().isBlank()) {
                isMatchDeparture = flight.getDeparture().equals(flightEntry.getDeparture());
            }

            if (!flight.getArrival().isBlank()) {
                isMatchArrival = flight.getArrival().equals(flightEntry.getArrival());
            }

            if (!flight.getDepartureDate().isBlank()) {
                isMatchDepartureDate = flight.getDepartureDate().equals(flightEntry.getDepartureDate());
            }

            if (!flight.getArrivalDate().isBlank()) {
                isMatchArrivalDate = flight.getArrivalDate().equals(flightEntry.getArrivalDate());
            }

            if (!flight.getDepartureTime().isBlank()) {
                isMatchDepartureTime = flight.getDepartureTime().equals(flightEntry.getDepartureTime());
            }
            if (!flight.getArrivalTime().isBlank()) {
                isMatchArrivalTime = flight.getArrivalTime().equals(flightEntry.getArrivalTime());
            }

            if (isMatchFlightNUmber && isMatchDeparture && isMatchArrival && isMatchDepartureDate && isMatchArrivalDate &&
                    isMatchDepartureTime && isMatchArrivalTime) {
                filteredFlights.add(flightEntry);
            }
        }

        return filteredFlights;
    }
}
