package com.fms.fares;

import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import com.fms.exceptions.FMSException;
import com.fms.fares.models.Fare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FareService {
    private final List<String> locations;
    private final Logger logger;
    private final FareRepository fareRepository;
    private final JdbcTemplate jdbcTemplate;
    private final String FIND_ALL_QUERY = "SELECT * FROM fare;";
    private final String FIND_ALL_BY_DEPARTURE_QUERY = "SELECT * FROM fare WHERE departure = ?;";
    private final String FIND_ALL_BY_ARRIVAL_QUERY = "SELECT * FROM fare WHERE arrival = ?;";
    private final String FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY = "SELECT * FROM fare WHERE departure = ? AND arrival = ?;";

    @Autowired
    public FareService(FareRepository fareRepository, JdbcTemplate jdbcTemplate) {
        this.logger = LoggerFactory.getLogger(FareService.class);
        this.fareRepository = fareRepository;
        this.jdbcTemplate = jdbcTemplate;

        this.locations = new ArrayList<>();
        this.locations.add("colombo");
        this.locations.add("dubai");
        this.locations.add("london");
        this.locations.add("new york");
        this.locations.add("paris");
        this.locations.add("sydney");
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Fare> getSearchedFares(String departure, String arrival) {
        RowMapper<Fare> rowMapper = (resultSet, rowNum) -> new Fare(
                resultSet.getInt("id"),
                resultSet.getString("departure"),
                resultSet.getString("arrival"),
                resultSet.getDouble("fare")
        );
        if (departure.isEmpty() && arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
        else if (arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY, rowMapper, departure);
        else if (departure.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_ARRIVAL_QUERY, rowMapper, arrival);
        else
            return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY, rowMapper, departure, arrival);
    }

    public Fare createFare(Fare fare) {
        validateInputs(fare);
        checkMissingData(fare);
        checkDuplicateFares(fare);
        return this.fareRepository.save(fare);
    }

    public Fare editFare(Fare userFare) {
        validateInputs(userFare);
        checkMissingDataWithId(userFare);
        checkDuplicateFaresWithId(userFare);
        checkObjectExistence(userFare);
        this.fareRepository.save(userFare);
        return userFare;
    }

    public int deleteFare(int id) {
        try {
            this.fareRepository.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            logger.error("an entry doesn't exist for the given id [{}]", id);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

    // utility functions

    private boolean isDuplicate(String departure, String arrival) {
        return this.fareRepository.findFirstByDepartureAndArrival(departure, arrival) != null;
    }
    private boolean isDuplicateWithId(String departure, String arrival, int id) {
        return this.fareRepository.findFirstByDepartureAndArrivalAndIdNot(departure, arrival, id) != null;
    }

    // validations

    private void validateInputs(Fare userFare) {
        checkSameLocation(userFare);
        checkNegativeValues(userFare);
        checkEmptyStrings(userFare);
    }
    private void checkMissingData(Fare fare) {
        if ((fare.getDeparture() == null) || (fare.getArrival() == null) || (fare.getFare() == 0)) {
            logger.error("missing data from the query | departure [{}], arrival [{}], fare [{}]",
                    fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }
    private void checkMissingDataWithId(Fare fare) {
        if ((fare.getId() == 0)
                || (fare.getDeparture() == null)
                || (fare.getArrival() == null)
                || (fare.getFare() == 0)) {
            logger.error("missing data from the query | id [{}], departure [{}], arrival [{}], fare [{}]",
                    fare.getId(), fare.getDeparture(), fare.getArrival(), fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.WRONG_INPUTS_FOUND);
        }
    }
    private void checkSameLocation(Fare fare) {
        if (fare.getDeparture().equalsIgnoreCase(fare.getArrival())) {
            logger.error("departure [{}] and arrival [{}] are not distinct",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.SAME_ARRIVAL_DEPARTURE_FOUND);
        }
    }
    private void checkNegativeValues(Fare fare) {
        if (fare.getFare() < 0) {
            logger.error("fare is negative [{}]", fare.getFare());
            throw new FMSException(HttpStatusCodesFMS.NEGATIVE_NUMBER);
        }
    }
    private void checkDuplicateFares(Fare fare) {
        if (isDuplicate(fare.getDeparture(), fare.getArrival())) {
            logger.error("a duplicate fare exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }
    private void checkDuplicateFaresWithId(Fare fare) {
        if (isDuplicateWithId(fare.getDeparture(), fare.getArrival(), fare.getId())) {
            logger.error("a duplicate fare exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
    }
    private void checkEmptyStrings(Fare fare) {
        if (fare.getDeparture().isEmpty() || fare.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }
    private void checkObjectExistence(Fare userFare) {
        if (!this.fareRepository.existsById(userFare.getId())) {
            logger.error("a fare doesn't exist for the given id [{}]", userFare.getId());
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }
}
