package com.fms.flights.repositories;

import com.fms.flights.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepositoryForFilterData {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightRepositoryForFilterData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> findAllByGivenOptions(String flightNUmber,
                                              String departure,
                                              String arrival,
                                              String departureDate,
                                              String departureTime,
                                              String arrivalDate,
                                              String arrivalTime) {
        String query = buildQueryForSearch(flightNUmber, departure, arrival, departureDate, departureTime, arrivalDate, arrivalTime);
        BeanPropertyRowMapper<Flight> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Flight.class);
        return jdbcTemplate.query(query, beanPropertyRowMapper);
    }

    private String buildQueryForSearch(String flightNUmber,
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
        return queryStr.toString();
    }

    private String buildConditionsListInSearchQuery(String flightNUmber,
                                                    String departure,
                                                    String arrival,
                                                    String departureDate,
                                                    String departureTime,
                                                    String arrivalDate,
                                                    String arrivalTime) {

        List<String> conditionsList = new ArrayList<>();

        if (!flightNUmber.isBlank()) {
            conditionsList.add("flight_number = " + "'" + flightNUmber + "'");
        }
        if (!departure.isBlank()) {
            conditionsList.add("departure = " + "'" + departure + "'");
        }
        if (!arrival.isBlank()) {
            conditionsList.add("arrival = " + "'" + arrival + "'");
        }
        if (!departureDate.isBlank()) {
            conditionsList.add("departure_date = " + "'" + departureDate + "'");
        }
        if (!departureTime.isBlank()) {
            conditionsList.add("departure_time = " + "'" + departureTime + "'");
        }
        if (!arrivalDate.isBlank()) {
            conditionsList.add("arrivalDate = " + "'" + arrivalDate + "'");
        }
        if (!arrivalTime.isBlank()) {
            conditionsList.add("arrival_time = " + "'" + arrivalTime + "'");
        }

        return String.join(" AND ", conditionsList);
    }
}
