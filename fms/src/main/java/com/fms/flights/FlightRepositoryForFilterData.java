package com.fms.flights;

import com.fms.flights.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightRepositoryForFilterData {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FlightRepositoryForFilterData(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Flight> findAllByGivenOptions(String query){
        BeanPropertyRowMapper<Flight> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Flight.class);
        return jdbcTemplate.query(query,beanPropertyRowMapper);
    }
}
