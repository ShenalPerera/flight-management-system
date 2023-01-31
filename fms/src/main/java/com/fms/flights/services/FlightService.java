package com.fms.flights.services;

import com.fms.flights.repositories.FlightRepositoryForFilterData;
import com.fms.flights.repositories.FlightRepositoryFMS;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.flights.models.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class FlightService {
    private final FlightRepositoryFMS flightRepositoryFMS;

    private final FlightRepositoryForFilterData flightRepositoryForFilterData;
    private final Logger logger;
    @Autowired
    FlightService(
            FlightRepositoryFMS flightRepositoryFMS, FlightRepositoryForFilterData flightRepositoryForFilterData){
        this.flightRepositoryFMS = flightRepositoryFMS;
        this.flightRepositoryForFilterData = flightRepositoryForFilterData;
        this.logger = LoggerFactory.getLogger(FlightService.class);
    }
    public List<Flight> getAllFlights(){
        logger.info("Calling method : {}","[flightRepository.findAll()");
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

        logger.info( "Query created : " + queryStr);

        List<Flight> filteredFlights =  flightRepositoryForFilterData.findAllByGivenOptions(queryStr.toString());
        logger.info("resultant size of the list : {}",filteredFlights.size());
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
        return validateAndCreateFlight(flight);
    }

    public Flight editFlight(Flight flight){
        return validateAndEdit(flight);
    }

    public void deleteFlight(String flightId){
        try {
            logger.info("Deleting flight : [flightId : {}]",flightId);
            flightRepositoryFMS.deleteById(flightId);
            logger.info("Flight deleted : Success [flightId : {}]",flightId);
        }
        catch (EmptyResultDataAccessException e){
            logger.error("Delete operation : Not success - No entry found with flightId : [{}]",flightId);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }


    private void validateFlightEntryFields(Flight flight){
        if (flight.isContainsEmptyFields()){
            logger.error("Invalid data : Flight contains empty values - [{}]",flight);
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())){
            logger.error("Invalid data : departure date and arrival date cannot be same");
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() +"T" + flight.getDepartureTime());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrivalDate() + "T" + flight.getArrivalTime());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)){
            logger.error("Invalid data: Same flight can not have multiple entries on same date");
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private Flight validateAndEdit(Flight flight){
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDateOrFlightId(
                flight.getFlightNumber(),
                flight.getDepartureDate(),
                flight.getFlightId()
        );
        logger.info("Elements in the list : {}", filteredFlights);

        if (filteredFlights.isEmpty()){
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
        else if (filteredFlights.size() == 1 && Objects.equals(filteredFlights.remove(0).getFlightId(), flight.getFlightId())){
            logger.info("Validated flight : success [editFlight]");
            try{
                return flightRepositoryFMS.save(flight);
            }
            catch (OptimisticLockingFailureException e){
                logger.error("Record was already edited");
                throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
            }
        }
        else{
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    private Flight validateAndCreateFlight(Flight flight){
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepositoryFMS.findAllByFlightNumberAndDepartureDate(
                flight.getFlightNumber(),
                flight.getDepartureDate()
        );

        if (filteredFlights.isEmpty()){
            logger.info("Validated flight : success [addNewFlight]");
            return flightRepositoryFMS.save(flight);
        }
        logger.error("Flight data is not valid : Duplicate entry found!");
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }
}
