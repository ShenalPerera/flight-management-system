package com.fms.fares.daos;

import com.fms.fares.models.Fare;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareDaoImpl implements FareDao {
    private final JdbcTemplate jdbcTemplate;
    public FareDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Fare> getSearchedFares(String departure, String arrival) {

        String FIND_ALL_QUERY = "SELECT * FROM fare ORDER BY fare_id DESC;";
        String FIND_ALL_BY_DEPARTURE_QUERY = "SELECT * FROM fare WHERE departure = ? ORDER BY fare_id DESC;";
        String FIND_ALL_BY_ARRIVAL_QUERY = "SELECT * FROM fare WHERE arrival = ? ORDER BY fare_id DESC;";
        String FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY = "SELECT * FROM fare WHERE departure = ? AND arrival = ? ORDER BY fare_id DESC;";

        RowMapper<Fare> rowMapper = (resultSet, rowNum) -> new Fare(
                resultSet.getInt("fare_id"),
                resultSet.getString("departure"),
                resultSet.getString("arrival"),
                resultSet.getDouble("fare"),
                resultSet.getTimestamp("created_date_time"),
                resultSet.getTimestamp("modified_date_time"),
                resultSet.getLong("version")
        );

        if (departure.isEmpty() && arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
        if (arrival.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY, rowMapper, departure);
        if (departure.isEmpty())
            return jdbcTemplate.query(FIND_ALL_BY_ARRIVAL_QUERY, rowMapper, arrival);
        return jdbcTemplate.query(FIND_ALL_BY_DEPARTURE_QUERY_AND_ARRIVAL_QUERY, rowMapper, departure, arrival);
    }
}
