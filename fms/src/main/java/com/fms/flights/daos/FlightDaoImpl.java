package com.fms.flights.daos;

import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightDaoImpl implements FlightDao {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger;

    @Autowired
    public FlightDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.logger = LoggerFactory.getLogger(FlightDaoImpl.class);
    }

    public List<Flight> findAllFlightsByGivenOptions(SearchFlightDTO searchFlightDTO) {

        String query = buildQueryForSearch(searchFlightDTO);

        logger.info("Query created : {}", query);

        BeanPropertyRowMapper<Flight> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Flight.class);
        return jdbcTemplate.query(query, beanPropertyRowMapper);
    }

    private String buildQueryForSearch(SearchFlightDTO searchFlightDTO) {
        StringBuilder queryStr = new StringBuilder();

        queryStr.append("SELECT *        ");
        queryStr.append("FROM flight     ");

        if (searchFlightDTO.isContainsNonEmptyNotNullValues()) {
            queryStr.append("WHERE ");
            String conditions = buildConditionsListInSearchQuery(searchFlightDTO);
            queryStr.append(conditions);
        }

        return queryStr.toString();
    }

    private String buildConditionsListInSearchQuery(SearchFlightDTO searchFlightDTO) {

        List<String> conditionsList = new ArrayList<>();

        if (!searchFlightDTO.getFlightNumber().isBlank()) {
            conditionsList.add("flight_number = " + "'" + searchFlightDTO.getFlightNumber() + "'");
        }
        if (!searchFlightDTO.getDeparture().isBlank()) {
            conditionsList.add("departure = " + "'" + searchFlightDTO.getDeparture() + "'");
        }
        if (!searchFlightDTO.getArrival().isBlank()) {
            conditionsList.add("arrival = " + "'" + searchFlightDTO.getArrival() + "'");
        }
        if (!searchFlightDTO.getDepartureDate().isBlank()) {
            conditionsList.add("departure_date = " + "'" + searchFlightDTO.getDepartureDate() + "'");
        }
        if (!searchFlightDTO.getDepartureTime().isBlank()) {
            conditionsList.add("departure_time = " + "'" + searchFlightDTO.getDepartureTime() + "'");
        }
        if (!searchFlightDTO.getArrivalDate().isBlank()) {
            conditionsList.add("arrival_date = " + "'" + searchFlightDTO.getArrivalDate() + "'");
        }
        if (!searchFlightDTO.getArrivalTime().isBlank()) {
            conditionsList.add("arrival_time = " + "'" + searchFlightDTO.getArrivalTime() + "'");
        }

        return String.join(" AND ", conditionsList);
    }
}
