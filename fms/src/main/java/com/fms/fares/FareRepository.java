package com.fms.fares;

import com.fms.fares.models.Fare;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FareRepository extends CrudRepository<Fare, Integer> {
    Fare findFirstByDepartureAndArrival(String departure, String arrival);
    List<Fare> findByDepartureAndArrivalOrId(String departure, String arrival, int id);
}
