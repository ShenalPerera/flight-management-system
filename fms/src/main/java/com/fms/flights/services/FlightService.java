package com.fms.flights.services;

import com.fms.exceptions.FMSException;
import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;
import com.fms.flights.repositories.FlightRepository;
import com.fms.flights.repositories.FlightDao;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    private final FlightDao flightDao;
    private final Logger logger;

    @Autowired
    FlightService(
            FlightRepository flightRepository, FlightDao flightDao) {
        this.flightRepository = flightRepository;
        this.flightDao = flightDao;
        this.logger = LoggerFactory.getLogger(FlightService.class);
    }

    public List<Flight> getAllFlights() {
        logger.info("Calling method : {}", "[flightRepository.findAll()");
        return flightRepository.findAll();
    }

    public List<Flight> getFilteredFlightsBySearchOptions(SearchFlightDTO searchFlightDTO) {


        List<Flight> filteredFlights = flightDao.findAllByGivenOptions(searchFlightDTO);
        logger.info("resultant size of the list : {}", filteredFlights.size());
        return filteredFlights;
    }

    public Flight addNewFlight(Flight flight) {
        return validateAndCreateFlight(flight);
    }

    public Flight editFlight(Flight flight) {
        return validateAndEdit(flight);
    }

    public void deleteFlight(String flightId) {
        try {
            logger.info("Deleting flight : [flightId : {}]", flightId);
            flightRepository.deleteById(flightId);
            logger.info("Flight deleted : Success [flightId : {}]", flightId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Delete operation : Not success - No entry found with flightId : [{}]", flightId);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }


    private void validateFlightEntryFields(Flight flight) {
        if (flight.isContainsEmptyFields()) {
            logger.error("Invalid data : Flight contains empty values - [{}]", flight);
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())) {
            logger.error("Invalid data : departure date and arrival date cannot be same");
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() + "T" + flight.getDepartureTime());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrivalDate() + "T" + flight.getArrivalTime());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)) {
            logger.error("Invalid data: Same flight can not have multiple entries on same date");
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }

    private Flight validateAndEdit(Flight flight) {
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepository.findAllByFlightNumberAndDepartureDateOrFlightId(
                flight.getFlightNumber(),
                flight.getDepartureDate(),
                flight.getFlightId()
        );
        logger.info("Elements in the list : {}", filteredFlights);

        if (filteredFlights.isEmpty()) {
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
        else if (filteredFlights.size() == 1 && Objects.equals(filteredFlights.remove(0).getFlightId(), flight.getFlightId())) {
            logger.info("Validated flight : success [editFlight]");
            try {
                return flightRepository.save(flight);
            } catch (OptimisticLockingFailureException e) {
                logger.error("Record was already edited");
                throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
            }
        } else {
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    private Flight validateAndCreateFlight(Flight flight) {
        validateFlightEntryFields(flight);

        List<Flight> filteredFlights = this.flightRepository.findAllByFlightNumberAndDepartureDate(
                flight.getFlightNumber(),
                flight.getDepartureDate()
        );

        if (filteredFlights.isEmpty()) {
            logger.info("Validated flight : success [addNewFlight]");
            return flightRepository.save(flight);
        }
        logger.error("Flight data is not valid : Duplicate entry found!");
        throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
    }
}
