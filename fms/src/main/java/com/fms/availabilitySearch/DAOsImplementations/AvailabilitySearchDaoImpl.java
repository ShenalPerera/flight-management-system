package com.fms.availabilitySearch.DAOsImplementations;

import com.fms.availabilitySearch.DAOs.AvailabilitySearchDao;
import com.fms.availabilitySearch.models.AvailableSearch;
import com.fms.fares.models.Fare;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AvailabilitySearchDaoImpl implements AvailabilitySearchDao {

    private final JdbcTemplate jdbcTemplate;

    public AvailabilitySearchDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AvailableSearch> getAvailableFlights(AvailableSearch availableSearch) {
        String query = buildQuery(availableSearch);
        RowMapper<AvailableSearch> rowMapper = (resultSet, rowNum) -> new AvailableSearch(
                resultSet.getString("flightNumber"),
                resultSet.getString("departure"),
                resultSet.getString("arrival"),
                resultSet.getString("departureDate"),
                resultSet.getString("departureTime"),
                resultSet.getString("arrivalDate"),
                resultSet.getString("arrivalTime"),
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
        return String.join(" AND ", conditionList);
    }
}
