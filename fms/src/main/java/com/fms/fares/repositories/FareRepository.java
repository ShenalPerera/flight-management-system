package com.fms.fares.repositories;

import com.fms.fares.models.Fare;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FareRepository extends CrudRepository<Fare, Integer> {
    List<Fare> findByDepartureAndArrivalOrFareId(String departure, String arrival, int fareId);
}
