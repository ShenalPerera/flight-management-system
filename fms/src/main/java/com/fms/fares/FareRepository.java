package com.fms.fares;

import com.fms.fares.models.Fare;
import org.springframework.data.repository.CrudRepository;

public interface FareRepository extends CrudRepository<Fare, Integer> {
    Fare findFirstByDepartureAndArrival(String departure, String arrival);
    Fare findFirstByDepartureAndArrivalAndIdNot(String departure, String arrival, int id);
}
