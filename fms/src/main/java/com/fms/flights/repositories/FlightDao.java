package com.fms.flights.repositories;

import com.fms.flights.models.Flight;
import com.fms.flights.models.SearchFlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Flight> findAllByGivenOptions(SearchFlightDTO searchFlightDTO) {

        String query = buildQueryForSearch(searchFlightDTO);
        BeanPropertyRowMapper<Flight> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Flight.class);
        return jdbcTemplate.query(query, beanPropertyRowMapper);
    }

    private String buildQueryForSearch(SearchFlightDTO searchFlightDTO) {
        StringBuilder queryStr = new StringBuilder();

        queryStr.append("SELECT *        ");
        queryStr.append("FROM flight     ");
        queryStr.append("WHERE           ");

        String conditions = buildConditionsListInSearchQuery(searchFlightDTO);

        queryStr.append(conditions);
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
