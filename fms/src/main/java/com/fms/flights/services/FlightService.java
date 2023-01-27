package com.fms.flights.services;

import com.fms.flights.FlightRepositoryForFilterData;
import com.fms.flights.repositories.FlightRepositoryFMS;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.flights.models.Flight;
import com.fms.flights.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightRepositoryFMS flightRepositoryFMS;

    private final FlightRepositoryForFilterData flightRepositoryForFilterData;
    private final Logger logger;
    @Autowired
    FlightService(FlightRepository flightRepository, FlightRepositoryFMS flightRepositoryFMS, FlightRepositoryForFilterData flightRepositoryForFilterData){
        this.flightRepository = flightRepository;
        this.flightRepositoryFMS = flightRepositoryFMS;
        this.flightRepositoryForFilterData = flightRepositoryForFilterData;
        this.logger = LoggerFactory.getLogger(FlightService.class);
    }
    public List<Flight> getAllFlights(){
        return flightRepositoryFMS.findAll();
    }

    public List<Flight> getFilteredFlightsBySearchOptions(String flightNUmber,
                                                          String departure,
                                                          String arrival,
                                                          String departureDate,
                                                          String departureTime,
                                                          String arrivalDate,
                                                          String arrivalTime) {

        StringBuilder queryStr = new StringBuilder();

        queryStr.append("SELECT *        ");
        queryStr.append("FROM flight     ");
        queryStr.append("WHERE           ");

        String conditions = buildConditionsListInSearchQuery(
                flightNUmber,
                departure,
                arrival,
                departureDate,
                departureTime,
                arrivalDate,
                arrivalTime
        );

        queryStr.append(conditions);

        logger.info( "created query : " + queryStr);

        List<Flight> filteredFlights =  flightRepositoryForFilterData.findAllByGivenOptions(queryStr.toString());
        logger.info("resultant size of the list : {}",filteredFlights);
        return filteredFlights;
    }

    private String buildConditionsListInSearchQuery(String flightNUmber,
                                                    String departure,
                                                    String arrival,
                                                    String departureDate,
                                                    String departureTime,
                                                    String arrivalDate,
                                                    String arrivalTime){

        List<String> conditionsList = new ArrayList<>();

        if (!flightNUmber.isBlank()){
            conditionsList  .add("flight_number = " + "'" +flightNUmber +"'");
        }
        if (!departure.isBlank()){
            conditionsList.add("departure = " + "'" + departure + "'");
        }
        if (!arrival.isBlank()){
            conditionsList.add("arrival = " + "'" + arrival + "'");
        }
        if (!departureDate.isBlank()){
            conditionsList.add("departure_date = " + "'" +departureDate + "'");
        }
        if (!departureTime.isBlank()){
            conditionsList.add("departure_time = " + "'"+ departureTime + "'");
        }
        if (!arrivalDate.isBlank()){
            conditionsList.add("arrivalDate = " + "'" + arrivalDate + "'");
        }
        if (!arrivalTime.isBlank()){
            conditionsList.add("arrival_time = " + "'" + arrivalTime + "'");
        }

        return String.join(" AND ", conditionsList);
    }

    public Flight addNewFlight(Flight flight ){
        if (isFlightValidForCreate(flight)){
            return flightRepositoryFMS.save(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public Flight editFlight(Flight flight){
        if (isFlightValidForEdit(flight)){
            return flightRepositoryFMS.save(flight);
        }
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }

    public void deleteFlight(String flightId){
        try {
            flightRepositoryFMS.deleteById(flightId);
        }
        catch (EmptyResultDataAccessException e){
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }


    private void validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() +"T" + flight.getDepartureTime());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrivalDate() + "T" + flight.getArrivalTime());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private boolean isFlightValidForEdit(Flight flight){

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDateOrFlightId(
                flight.getFlightNumber(),
                flight.getDepartureDate(),
                flight.getFlightId()
        );
        return filteredFlights.size() == 1 && Objects.equals(filteredFlights.remove(0).getFlightId(), flight.getFlightId());
    }

    private boolean isFlightValidForCreate(Flight flight){
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDate(
                flight.getFlightNumber(),
                flight.getDepartureDate()
        );

        return filteredFlights.isEmpty();
    }
}
