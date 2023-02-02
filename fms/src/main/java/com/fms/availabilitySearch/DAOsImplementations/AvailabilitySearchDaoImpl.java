package com.fms.availabilitySearch.DAOsImplementations;

import com.fms.availabilitySearch.DAOs.AvailabilitySearchDao;
import com.fms.availabilitySearch.models.AvailableFlight;
import com.fms.availabilitySearch.models.AvailableSearch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilitySearchDaoImpl implements AvailabilitySearchDao {

    private final JdbcTemplate jdbcTemplate;

    public AvailabilitySearchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AvailableFlight> getAvailableFlights(AvailableSearch availableSearch) {
        String query = buildQuery(availableSearch);
        RowMapper<AvailableFlight> rowMapper = (resultSet, rowNum) -> new AvailableFlight(
                resultSet.getString("flight_number"),
                resultSet.getString("departure"),
                resultSet.getString("arrival"),
                resultSet.getString("departure_date"),
                resultSet.getString("departure_time"),
                resultSet.getString("arrival_date"),
                resultSet.getString("arrival_time"),
                resultSet.getDouble("fare")
        );
        return jdbcTemplate.query(query, rowMapper);
    }

    private String buildQuery(AvailableSearch availableSearch) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM route, flight, fare WHERE ");
        query.append(getQueryConditions(availableSearch));
        return query.toString();
    }

    private String getQueryConditions(AvailableSearch availableSearch) {
        List<String> conditionList = new ArrayList<>();
        conditionList.add("route.departure = flight.departure");
        conditionList.add("route.destination = flight.arrival");
        conditionList.add("route.departure = fare.departure");
        conditionList.add("route.destination = fare.arrival");

        if (!availableSearch.getFlightNumber().isEmpty()) {
            conditionList.add("flight.flight_number = '" + availableSearch.getFlightNumber() + "'");
        }
        if (!availableSearch.getDeparture().isEmpty()) {
            conditionList.add("route.departure = '" + availableSearch.getDeparture() + "'");
        }
        if (!availableSearch.getArrival().isEmpty()) {
            conditionList.add("route.destination = '" + availableSearch.getArrival() + "'");
        }
        if (!availableSearch.getDepartureStartDate().isEmpty()) {
            conditionList.add("flight.departure_date >= '" + availableSearch.getDepartureStartDate() + "'");
        }
        if (!availableSearch.getDepartureEndDate().isEmpty()) {
            conditionList.add("flight.departure_date <= '" + availableSearch.getDepartureEndDate() + "'");
        }

        return String.join(" AND ", conditionList);
    }
}
