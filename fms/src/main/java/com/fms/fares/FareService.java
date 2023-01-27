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

import java.util.List;

@Service
public class FareService {
    private final Logger logger;
    private final FareRepository fareRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FareService(FareRepository fareRepository, JdbcTemplate jdbcTemplate) {
        this.logger = LoggerFactory.getLogger(FareService.class);
        this.fareRepository = fareRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Fare> getSearchedFares(String departure, String arrival) {

        String FIND_ALL_QUERY = "SELECT * FROM fare;";
        String FIND_ALL_BY_DEPARTURE_QUERY = "SELECT * FROM fare WHERE departure = ?;";
        String FIND_ALL_BY_ARRIVAL_QUERY = "SELECT * FROM fare WHERE arrival = ?;";
        String FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY = "SELECT * FROM fare WHERE departure = ? AND arrival = ?;";

        RowMapper<Fare> rowMapper = (resultSet, rowNum) -> new Fare(
                resultSet.getInt("id"),
                resultSet.getString("departure"),
                resultSet.getString("arrival"),
                resultSet.getDouble("fare")
        );

        if (departure.isEmpty() && arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
        if (arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY, rowMapper, departure);
        if (departure.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_ARRIVAL_QUERY, rowMapper, arrival);
        return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY, rowMapper, departure, arrival);
    }

    public Fare createFare(Fare fare) {
        validateInputs(fare);
        checkMissingData(fare);
        checkDuplicateFares(fare);
        return fareRepository.save(fare);
    }

    public Fare editFare(Fare userFare) {
        validateInputs(userFare);
        checkMissingDataWithId(userFare);
        checkDuplicateFaresAndExistence(userFare);
        return fareRepository.save(userFare);
    }

    public int deleteFare(int id) {
        try {
            fareRepository.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            logger.error("an entry doesn't exist for the given id [{}]", id);
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }

    // utility functions

    private boolean isDuplicate(String departure, String arrival) {
        return fareRepository.findFirstByDepartureAndArrival(departure, arrival) != null;
    }
    private List<Fare> getFaresForValidation(String departure, String arrival, int id) {
        return fareRepository.findByDepartureAndArrivalOrId(departure, arrival, id);
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
    private void checkDuplicateFaresAndExistence(Fare fare) {
        List<Fare> fareList = getFaresForValidation(fare.getDeparture(), fare.getArrival(), fare.getId());
        if (fareList.size() > 1) {
            logger.error("a duplicate fare exists for the given inputs");
            throw new FMSException(HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND);
        }
        if (fareList.isEmpty() || (fareList.get(0).getId() != fare.getId())) {
            logger.error("a fare doesn't exist for the given id [{}]", fare.getId());
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        }
    }
    private void checkEmptyStrings(Fare fare) {
        if (fare.getDeparture().isEmpty() || fare.getArrival().isEmpty()) {
            logger.error("the query contains empty strings | departure [{}], arrival [{}]",
                    fare.getDeparture(), fare.getArrival());
            throw new FMSException(HttpStatusCodesFMS.EMPTY_FIELD_FOUND);
        }
    }
}
