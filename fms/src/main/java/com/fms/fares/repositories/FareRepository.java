package com.fms.fares.repositories;

import com.fms.fares.models.Fare;
import org.springframework.data.repository.CrudRepository;

public interface FareRepository extends CrudRepository<Fare, Integer> {}
