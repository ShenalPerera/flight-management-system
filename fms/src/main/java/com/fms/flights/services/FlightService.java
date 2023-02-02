package com.fms.flights.services;

import com.fms.exceptions.FMSException;
import com.fms.flights.daos.FlightDao;
import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;
import com.fms.flights.repositories.FlightRepository;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.routes.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    private final FlightDao flightDao;

    private final RouteRepository routeRepository;
    private final Logger logger;

    @Autowired
    FlightService(
            FlightRepository flightRepository, FlightDao flightDao, RouteRepository routeRepository) {
        this.flightRepository = flightRepository;
        this.flightDao = flightDao;
        this.routeRepository = routeRepository;
        this.logger = LoggerFactory.getLogger(FlightService.class);
    }

    public List<Flight> getAllFlights() {
        logger.info("Calling method : {}", "[flightRepository.findAll()");
        return flightRepository.findAll();
    }

    public List<Flight> getFilteredFlightsBySearchOptions(SearchFlightDTO searchFlightDTO) {
        List<Flight> filteredFlights = flightDao.findAllFlightsByGivenOptions(searchFlightDTO);
        logger.info("resultant size of the list : {}", filteredFlights.size());
        return filteredFlights;
    }

    public Flight addNewFlight(Flight flight) {
        logger.info("Flight create : started");
        validateFlightForCreate(flight);
        logger.info("Flight create : Finished -- [Success]");
        return this.flightRepository.save(flight);
    }

    public Flight editFlight(Flight flight) {
        validateFlightForEdit(flight);
        try {
            return flightRepository.save(flight);
        } catch (OptimisticLockingFailureException e) {
            logger.error("Record was already edited");
            throw new FMSException(HttpStatusCodesFMS.VERSION_MISMATCHED);
        }
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

    private void validateFlightForCreate(Flight flight) {
        validateEmptyFlightsFieldForCreate(flight);
        validateFlightEntryFields(flight);
        validateExistenceOfRoute(flight);
        validateDuplicatesBeforeCreate(flight);
    }

    private void validateFlightForEdit(Flight flight) {
        validateEmptyFlightFiledForEdit(flight);
        validateFlightEntryFields(flight);
        validateExistenceOfRoute(flight);
        validateDuplicatesBeforeEdit(flight);
    }

    private void validateEmptyFlightsFieldForCreate(Flight flight) {
        boolean isContainsEmptyAttributes = flight.getFlightNumber().isBlank() ||
                flight.getDeparture().isBlank() ||
                flight.getArrival().isBlank() ||
                flight.getDepartureDate().isBlank() ||
                flight.getDepartureTime().isBlank() ||
                flight.getArrivalDate().isBlank() ||
                flight.getArrivalTime().isBlank();

        if (isContainsEmptyAttributes) {
            logger.error("Flight DTO has empty field(s) [operation -- create]: {}", flight);
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }

    private void validateEmptyFlightFiledForEdit(Flight flight) {
        if (flight.isContainsEmptyFields()) {
            logger.error("Flight DTO has empty field(s) [operation -- edit] : {}", flight);
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }

    private void validateFlightEntryFields(Flight flight) {
        if (flight.getDeparture().equalsIgnoreCase(flight.getArrival())) {
            logger.error("Invalid data : departure date and arrival date are same | departure : {} arrival :{}", flight.getDeparture(), flight.getArrival());
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
        LocalDateTime departureDateNTime = LocalDateTime.parse(flight.getDepartureDate() + "T" + flight.getDepartureTime());
        LocalDateTime arrivalDateNTime = LocalDateTime.parse(flight.getArrivalDate() + "T" + flight.getArrivalTime());

        if (departureDateNTime.isAfter(arrivalDateNTime) || departureDateNTime.isEqual(arrivalDateNTime)) {
            logger.error("Invalid data: Departure date and arrival dates are invalid");
            throw new FMSException(HttpStatusCodesFMS.INVALID_DEPARTURE_AND_ARRIVAL_DATE);
        }
    }


    private void validateDuplicatesBeforeCreate(Flight flight) {
        int count = this.flightRepository.countAllByFlightNumberAndDepartureDate(flight.getFlightNumber(), flight.getDeparture());
        if (count != 0) {
            logger.error("Duplicate entries found!");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    private void validateDuplicatesBeforeEdit(Flight flight) {
        boolean isValidForEdit = this.flightRepository.isFlightExitsAndNoDuplicatesFound(flight.getFlightNumber(), flight.getDepartureDate(), flight.getFlightId());

        if (!isValidForEdit) {
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }

    private void validateExistenceOfRoute(Flight flight) {
        boolean isRouteExists = this.routeRepository.existsRouteByDepartureAndDestination(flight.getDeparture(), flight.getArrival());

        if (!isRouteExists) {
            logger.error("Route not found for given departure and arrival");
            throw new FMSException(HttpStatusCodesFMS.ROUTE_DOESNT_EXIST);
        }
    }
}
