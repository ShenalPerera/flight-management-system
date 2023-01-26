package com.fms.flights;

import com.fms.flights.models.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FlightRepository {

    private final List<Flight> flights = new ArrayList<>();

    FlightRepository(){
        flights.add(new Flight("1", "1", "colombo", "India", "2023-01-04", "2023-01-05", "13:04", "11:00"));
    }


    public List<Flight> getAll(){
        return flights;
    }

    public Flight addEntry(Flight flight){
        flights.add(flight);
        return flight;
    }

    public Flight deleteEntryById(String id){
        Flight deletedFlight = flights.stream().filter(flight -> Objects.equals(flight.getId(), id)).findFirst().orElse(null);
        boolean isDeleted = flights.remove(deletedFlight);
        if (isDeleted){
            return deletedFlight;
        }
        return null;
    }

    public Flight editFlight(Flight editedFlight){
        try {
            return flights.set(flights.indexOf(editedFlight),editedFlight );
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    public List<Flight> getFlightsByFlightNumberNDepartureDate(Flight flight){
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDeparture_date() +"T" + flight.getDeparture_time());

        return flights.stream().filter(flightEntry ->
                Objects.equals(flightEntry.getFlight_number(), flight.getFlight_number()) &&
                departureDateNTime.isEqual(LocalDateTime.parse(flightEntry.getDeparture_date() +"T" + flightEntry.getDeparture_time())) &&
                !Objects.equals(flightEntry.getId(), flight.getId())

                ).
                collect(Collectors.toList());
    }

    public List<Flight> getFLightsByParameters(Flight flight){
        List<Flight> filteredFlights = new ArrayList<>();
        boolean isMatchFlightNUmber ;
        boolean isMatchDeparture ;
        boolean isMatchArrival ;
        boolean isMatchDepartureDate ;
        boolean isMatchArrivalDate ;
        boolean isMatchDepartureTime ;
        boolean isMatchArrivalTime ;

        for (Flight flightEntry : flights) {

            isMatchFlightNUmber =  true;
            isMatchDeparture = true;
            isMatchArrival = true;
            isMatchDepartureDate = true;
            isMatchArrivalDate = true;
            isMatchDepartureTime  = true;
            isMatchArrivalTime = true;

            if ( !flight.getFlight_number().isBlank()){
                isMatchFlightNUmber = flight.getFlight_number().equals(flightEntry.getFlight_number());

            }
            if ( !flight.getDeparture().isBlank()){
                isMatchDeparture = flight.getDeparture().equals(flightEntry.getDeparture());
            }

            if ( !flight.getArrival().isBlank()){
                isMatchArrival = flight.getArrival().equals(flightEntry.getArrival());
            }

            if ( !flight.getDeparture_date().isBlank()){
                isMatchDepartureDate = flight.getDeparture_date().equals(flightEntry.getDeparture_date());
            }

            if ( !flight.getArrival_date().isBlank()){
                isMatchArrivalDate = flight.getArrival_date().equals(flightEntry.getArrival_date());
            }

            if ( !flight.getDeparture_time().isBlank()){
                isMatchDepartureTime = flight.getDeparture_time().equals(flightEntry.getDeparture_time());
            }
            if ( !flight.getArrival_time().isBlank()){
                isMatchArrivalTime =flight.getArrival_time().equals(flightEntry.getArrival_time());
            }

            if (isMatchFlightNUmber && isMatchDeparture && isMatchArrival && isMatchDepartureDate && isMatchArrivalDate &&
                    isMatchDepartureTime && isMatchArrivalTime){
                filteredFlights.add(flightEntry);
            }
        }

        return filteredFlights;
    }
}
